package zed.rainxch.plscribbledash.game.data.repository

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.PorterDuff
import android.graphics.RectF
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.PathParser
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt
import androidx.core.graphics.withSave
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import zed.rainxch.plscribbledash.core.data.utils.managers.DataStoreManager
import zed.rainxch.plscribbledash.game.domain.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.game.domain.model.PaintPath
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath
import zed.rainxch.plscribbledash.game.domain.model.PathData
import zed.rainxch.plscribbledash.game.domain.repository.GameRepository
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.sqrt

class GameRepositoryImpl @Inject constructor(
    private val context: Context,
    private val dataStoreManager: DataStoreManager
) : GameRepository {


    override suspend fun earnCoin(
        score: Int,
        mode: DifficultyLevelOptions
    ) {
        val scoreInCoins = when(score) {
            in 0..39 -> 1
            in 40 .. 79 -> 2
            in 80 .. 89 -> 4
            in 90 .. 100 -> 6
            else -> 0
        }

        val scopeMultiplied = (scoreInCoins * mode.scoreMultiplier).toInt()

        dataStoreManager.setCoins(dataStoreManager.coins.first() + scopeMultiplied)
    }

    private fun parseDimensionValue(value: String): Int {
        if (value.isEmpty()) return 0

        try {
            if (value.startsWith("@")) {
                val resourceId = value.substring(1).toIntOrNull() ?: 0
                return context.resources.getDimensionPixelSize(resourceId)
            }

            if (value.all { it.isDigit() }) {
                return value.toInt()
            }

            val numericPart = value.takeWhile { it.isDigit() || it == '.' }
            val unit = value.substring(numericPart.length)

            val numericValue = numericPart.toFloatOrNull() ?: return 0

            return when (unit) {
                "dp", "dip" -> (numericValue * context.resources.displayMetrics.density).toInt()
                "sp" -> (numericValue * context.resources.displayMetrics.scaledDensity).toInt()
                "px" -> numericValue.toInt()
                "in" -> (numericValue * context.resources.displayMetrics.xdpi).toInt()
                "mm" -> (numericValue * context.resources.displayMetrics.xdpi / 25.4f).toInt()
                "pt" -> (numericValue * context.resources.displayMetrics.xdpi / 72f).toInt()
                "" -> numericValue.toInt() // Assume pixels if no unit specified
                else -> 0 // Unknown unit
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
    }

    override fun getPathData(drawableResId: Int): ParsedPath {
        val parser = context.resources.getXml(drawableResId)

        val paths = mutableListOf<PathData>()
        var width = 0
        var height = 0
        var viewportWidth = 0f
        var viewportHeight = 0f

        try {
            var eventType = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "vector" -> {
                                // Parse vector attributes
                                for (i in 0 until parser.attributeCount) {
                                    val attrName = parser.getAttributeName(i)
                                    val attrValue = parser.getAttributeValue(i)

                                    when (attrName) {
                                        "width" -> width = parseDimensionValue(attrValue)
                                        "height" -> height = parseDimensionValue(attrValue)
                                        "viewportWidth" -> viewportWidth = attrValue.toFloat()
                                        "viewportHeight" -> viewportHeight = attrValue.toFloat()
                                    }
                                }
                            }

                            "path" -> {
                                // Parse path attributes
                                var pathData = ""
                                var strokeWidth = 0f
                                var fillColor = ""
                                var strokeColor = ""

                                for (i in 0 until parser.attributeCount) {
                                    val attrName = parser.getAttributeName(i)
                                    val attrValue = parser.getAttributeValue(i)

                                    when (attrName) {
                                        "pathData" -> pathData = attrValue
                                        "strokeWidth" -> strokeWidth = attrValue.toFloat()
                                        "fillColor" -> fillColor = attrValue
                                        "strokeColor" -> strokeColor = attrValue
                                    }
                                }

                                paths.add(
                                    PathData(
                                        pathData,
                                        strokeWidth,
                                        fillColor,
                                        strokeColor
                                    )
                                )
                            }
                        }
                    }
                }
                eventType = parser.next()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            parser.close()
        }
        return ParsedPath(
            paths = paths,
            width = width,
            height = height,
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight
        )
    }

    override suspend fun getResultScore(
        userPaths: List<PaintPath>,
        exampleParsedPath: ParsedPath,
        difficultyLevelOption: DifficultyLevelOptions
    ): Int {
        return withContext(Dispatchers.Default) {
            // 1. Get stroke width multiplier based on difficulty - but ensure it's not too different
            // We're making this more consistent to avoid dramatic differences between difficulty levels
            val strokeWidthMultiplier = difficultyLevelOption.strokeWidthMultiplier

            // Get average stroke widths
            val userStrokeWidth = userPaths.map { it.strokeWidth }.average().toFloat()
            val exampleStrokeWidth =
                exampleParsedPath.paths.map { it.strokeWidth }.average().toFloat()

            // Calculate total path lengths and complexity metrics
            val userTotalPathLength = calculateUserPathsLength(userPaths)
            val exampleTotalPathLength = calculateExamplePathsLength(exampleParsedPath)
            val userDirectionChanges = calculateUserPathDirectionChanges(userPaths)
            val exampleDirectionChanges = calculateExampleDirectionChanges(exampleParsedPath)

            // 2a. Calculate bounds for both drawings
            val userBounds = calculateUserPathsBounds(userPaths)
            val exampleBounds = calculateExamplePathsBounds(exampleParsedPath)

            // 2b. Inset bounds
            val userInsetBounds = RectF(userBounds).apply {
                val insetAmount = userStrokeWidth / 2f + (exampleStrokeWidth - userStrokeWidth) / 2f
                inset(insetAmount, insetAmount)
            }

            val exampleInsetBounds = RectF(exampleBounds).apply {
                inset(exampleStrokeWidth / 2f, exampleStrokeWidth / 2f)
            }

            // 3. Create bitmaps for rendering
            val canvasSize = 1000 // Use a fixed size for the comparison canvas

            val userBitmap = createBitmap(canvasSize, canvasSize)
            val exampleBitmap = createBitmap(canvasSize, canvasSize)

            val userCanvas = Canvas(userBitmap)
            val exampleCanvas = Canvas(exampleBitmap)

            // Clear with transparent
            userCanvas.drawColor(Color.Transparent.toArgb(), PorterDuff.Mode.CLEAR)
            exampleCanvas.drawColor(Color.Transparent.toArgb(), PorterDuff.Mode.CLEAR)

            // 2c & 2d. Scale and translate to normalize paths
            normalizeAndDrawUserPaths(userCanvas, userPaths, userInsetBounds, canvasSize)
            normalizeAndDrawExamplePaths(
                exampleCanvas,
                exampleParsedPath,
                exampleInsetBounds,
                canvasSize,
                strokeWidthMultiplier
            )

            // 4-7. Compare pixels - Exactly as specified in the algorithm
            var visibleUserPixelCount = 0
            var matchingUserPixelCount = 0
            var totalExamplePixelCount = 0
            var nonMatchingUserPixelCount = 0

            val userPixels = IntArray(canvasSize * canvasSize)
            val examplePixels = IntArray(canvasSize * canvasSize)

            userBitmap.getPixels(userPixels, 0, canvasSize, 0, 0, canvasSize, canvasSize)
            exampleBitmap.getPixels(examplePixels, 0, canvasSize, 0, 0, canvasSize, canvasSize)

            for (i in userPixels.indices) {
                val userAlpha = android.graphics.Color.alpha(userPixels[i])
                val exampleAlpha = android.graphics.Color.alpha(examplePixels[i])

                // Count total example pixels for comparison
                if (exampleAlpha > 0) {
                    totalExamplePixelCount++
                }

                // 5. If transparent in both, ignore
                if (userAlpha == 0 && exampleAlpha == 0) {
                    continue
                }

                // 6. If non-transparent in user's bitmap, count as visible user pixel
                if (userAlpha > 0) {
                    visibleUserPixelCount++

                    // 7. If also non-transparent in example bitmap, count as matched pixel
                    if (exampleAlpha > 0) {
                        matchingUserPixelCount++
                    } else {
                        // Count user pixels that don't match example
                        nonMatchingUserPixelCount++
                    }
                }
            }

            // 8. Calculate coverage percentage - how well user drawing matches example
            val coveragePercentage = if (visibleUserPixelCount > 0) {
                (matchingUserPixelCount.toFloat() / visibleUserPixelCount.toFloat()) * 100
            } else {
                0f
            }

            // Calculate completeness percentage - how much of the example is covered
            val completenessPercentage = if (totalExamplePixelCount > 0) {
                (matchingUserPixelCount.toFloat() / totalExamplePixelCount.toFloat()) * 100
            } else {
                0f
            }

            // Calculate precision percentage - how precise the drawing is
            val precisionPercentage = if (visibleUserPixelCount > 0) {
                ((visibleUserPixelCount - nonMatchingUserPixelCount).toFloat() / visibleUserPixelCount.toFloat()) * 100
            } else {
                0f
            }

            // Clean up bitmaps to prevent memory leaks
            userBitmap.recycle()
            exampleBitmap.recycle()

            // Combine metrics into base score with weighted importance
            val baseScore = calculateBaseScore(
                coveragePercentage,
                completenessPercentage,
                precisionPercentage
            )

            // Apply normalized penalties that are consistent across difficulty levels
            val finalScore = applyConsistentPenalties(
                baseScore,
                userPaths.size,
                exampleParsedPath.paths.size,
                userTotalPathLength,
                exampleTotalPathLength,
                completenessPercentage,
                userDirectionChanges,
                exampleDirectionChanges,
                difficultyLevelOption
            )

            return@withContext finalScore
        }
    }



    /**
     * Calculate a balanced base score from the main metrics
     */
    private fun calculateBaseScore(
        coveragePercentage: Float,
        completenessPercentage: Float,
        precisionPercentage: Float
    ): Float {
        // Weight the components with more importance on completeness and coverage
        return (coveragePercentage * 0.4f) +         // 40% - How well user's drawing matches example
                (completenessPercentage * 0.4f) +     // 40% - How much of example is covered
                (precisionPercentage * 0.2f)          // 20% - How precise (not drawing outside lines)
    }

    /**
     * Apply consistent penalties across difficulty levels
     * This ensures more predictable scoring regardless of difficulty
     */
    private fun applyConsistentPenalties(
        baseScore: Float,
        userPathCount: Int,
        examplePathCount: Int,
        userPathLength: Float,
        examplePathLength: Float,
        completenessPercentage: Float,
        userDirectionChanges: Int,
        exampleDirectionChanges: Int,
        difficultyLevel: DifficultyLevelOptions
    ): Int {
        var adjustedScore = baseScore

        // Detect obvious line drawing cheats
        val isSimpleLine = userPathCount == 1 &&
                userDirectionChanges <= 2 &&
                userDirectionChanges < exampleDirectionChanges * 0.3f

        // 1. Apply line drawing penalty
        if (isSimpleLine && completenessPercentage < 40f) {
            // Apply consistent penalty for simple line cheating regardless of difficulty
            adjustedScore *= 0.3f  // 70% reduction
        }

        // 2. Check path count - only penalize when significantly different
        val pathRatio = userPathCount.toFloat() / maxOf(1f, examplePathCount.toFloat())
        if (pathRatio > 2.0f) {
            // Too many paths - apply small penalty
            val pathPenalty = minOf((pathRatio - 2.0f) * 5f, 20f) // Cap at 20%
            adjustedScore *= (1f - (pathPenalty / 100f))
        } else if (pathRatio < 0.5f && examplePathCount > 2) {
            // Too few paths for a complex example - small penalty
            val pathPenalty = minOf((0.5f - pathRatio) * 10f, 15f) // Cap at 15%
            adjustedScore *= (1f - (pathPenalty / 100f))
        }

        // 3. Check path length - only penalize when dramatically different
        val lengthRatio = userPathLength / maxOf(1f, examplePathLength)
        if (lengthRatio < 0.6f && completenessPercentage < 50f) {
            // Path is too short and doesn't cover enough - small penalty
            val lengthPenalty = minOf((0.6f - lengthRatio) * 25f, 20f) // Cap at 20%
            adjustedScore *= (1f - (lengthPenalty / 100f))
        }

        // 4. Apply difficulty adjustment (small modifier based on difficulty)
        val difficultyFactor = difficultyLevel.difficultyFactor

        adjustedScore *= difficultyFactor

        // 5. Handle extremely poor attempts more consistently
        if (baseScore < 20f) {
            // Normalize very low scores to prevent wild variations
            return minOf(
                (baseScore * 1.5f).toInt(),  // Allow slight boost to prevent always showing single digits
                30                           // Cap at 30 for very poor attempts
            )
        }

        // Make sure we don't exceed 100
        return minOf(adjustedScore.toInt(), 100)
    }

    /**
     * Calculate path length for user paths
     */
    private fun calculateUserPathsLength(paths: List<PaintPath>): Float {
        var totalLength = 0f

        for (path in paths) {
            if (path.points.size < 2) continue

            for (i in 1 until path.points.size) {
                val p1 = path.points[i-1]
                val p2 = path.points[i]

                val dx = p2.x - p1.x
                val dy = p2.y - p1.y
                totalLength += sqrt(dx * dx + dy * dy)
            }
        }

        return totalLength
    }

    /**
     * Calculate path length for example paths
     */
    private fun calculateExamplePathsLength(parsedPath: ParsedPath): Float {
        var totalLength = 0f

        for (pathData in parsedPath.paths) {
            val path = PathParser.createPathFromPathData(pathData.pathData)
            if (path != null) {
                val measure = PathMeasure(path, false)
                totalLength += measure.length
            }
        }

        return totalLength
    }

    /**
     * Calculate direction changes in user paths
     */
    private fun calculateUserPathDirectionChanges(paths: List<PaintPath>): Int {
        var totalDirectionChanges = 0

        for (path in paths) {
            if (path.points.size < 3) continue

            var prevDx = 0f
            var prevDy = 0f
            var directionEstablished = false

            for (i in 2 until path.points.size) {
                val p0 = path.points[i-2]
                val p1 = path.points[i-1]
                val p2 = path.points[i]

                val dx = p2.x - p1.x
                val dy = p2.y - p1.y

                val length = sqrt(dx * dx + dy * dy)
                val ndx = if (length > 0) dx / length else 0f
                val ndy = if (length > 0) dy / length else 0f

                if (!directionEstablished) {
                    prevDx = ndx
                    prevDy = ndy
                    directionEstablished = true
                    continue
                }

                val dotProduct = prevDx * ndx + prevDy * ndy

                if (dotProduct < 0.9f) {
                    totalDirectionChanges++
                    prevDx = ndx
                    prevDy = ndy
                }
            }
        }

        return maxOf(1, totalDirectionChanges)
    }

    /**
     * Calculate direction changes in example paths
     */
    private fun calculateExampleDirectionChanges(parsedPath: ParsedPath): Int {
        var totalDirectionChanges = 0

        for (pathData in parsedPath.paths) {
            val path = PathParser.createPathFromPathData(pathData.pathData)
            if (path != null) {
                val pathMeasure = PathMeasure(path, false)
                val pathLength = pathMeasure.length

                val numSamples = maxOf(3, (pathLength / 10f).toInt())
                val samplePoints = mutableListOf<Pair<Float, Float>>()

                for (i in 0 until numSamples) {
                    val distance = i * pathLength / (numSamples - 1)
                    val pos = floatArrayOf(0f, 0f)
                    val tan = floatArrayOf(0f, 0f)
                    pathMeasure.getPosTan(distance, pos, tan)
                    samplePoints.add(Pair(pos[0], pos[1]))
                }

                if (samplePoints.size >= 3) {
                    var prevDx = 0f
                    var prevDy = 0f
                    var directionEstablished = false

                    for (i in 2 until samplePoints.size) {
                        val p0 = samplePoints[i-2]
                        val p1 = samplePoints[i-1]
                        val p2 = samplePoints[i]

                        val dx = p2.first - p1.first
                        val dy = p2.second - p1.second

                        val length = sqrt(dx * dx + dy * dy)
                        val ndx = if (length > 0) dx / length else 0f
                        val ndy = if (length > 0) dy / length else 0f

                        if (!directionEstablished) {
                            prevDx = ndx
                            prevDy = ndy
                            directionEstablished = true
                            continue
                        }

                        val dotProduct = prevDx * ndx + prevDy * ndy

                        if (dotProduct < 0.9f) {
                            totalDirectionChanges++
                            prevDx = ndx
                            prevDy = ndy
                        }
                    }
                }
            }
        }

        return maxOf(1, totalDirectionChanges)
    }
    private fun calculateUserPathsBounds(paths: List<PaintPath>): RectF {
        val bounds = RectF()

        for (path in paths) {
            if (path.points.isNotEmpty()) {
                // Initialize with the first point
                val firstPoint = path.points.first()
                val pathBounds = RectF(firstPoint.x, firstPoint.y, firstPoint.x, firstPoint.y)

                // Expand with all other points
                for (point in path.points) {
                    pathBounds.left = minOf(pathBounds.left, point.x)
                    pathBounds.top = minOf(pathBounds.top, point.y)
                    pathBounds.right = maxOf(pathBounds.right, point.x)
                    pathBounds.bottom = maxOf(pathBounds.bottom, point.y)
                }

                // Expand by the stroke width
                pathBounds.inset(-path.strokeWidth / 2f, -path.strokeWidth / 2f)

                // Union with the overall bounds
                if (bounds.isEmpty) {
                    bounds.set(pathBounds)
                } else {
                    bounds.union(pathBounds)
                }
            }
        }

        return bounds
    }

    private fun calculateExamplePathsBounds(parsedPath: ParsedPath): RectF {
        val bounds = RectF()

        for (pathData in parsedPath.paths) {
            val path = PathParser.createPathFromPathData(pathData.pathData)
            if (path != null) {
                val pathBounds = RectF()
                path.computeBounds(pathBounds, true)

                // Expand by the stroke width
                pathBounds.inset(-pathData.strokeWidth / 2f, -pathData.strokeWidth / 2f)

                // Union with the overall bounds
                if (bounds.isEmpty) {
                    bounds.set(pathBounds)
                } else {
                    bounds.union(pathBounds)
                }
            }
        }

        return bounds
    }

    private fun normalizeAndDrawUserPaths(
        canvas: Canvas,
        paths: List<PaintPath>,
        bounds: RectF,
        canvasSize: Int
    ) {
        if (bounds.isEmpty) return

        canvas.withSave {

            // 2c. Offset to (0,0)
            // 2d. Scale to fit canvas while preserving aspect ratio
            val scaleX = canvasSize / bounds.width()
            val scaleY = canvasSize / bounds.height()
            val scale = min(scaleX, scaleY)

            // Center the drawing
            val scaledWidth = bounds.width() * scale
            val scaledHeight = bounds.height() * scale
            val translateX = (canvasSize - scaledWidth) / 2f - bounds.left * scale
            val translateY = (canvasSize - scaledHeight) / 2f - bounds.top * scale

            translate(translateX, translateY)
            scale(scale, scale)

            // Draw each path
            for (paintPath in paths) {
                if (paintPath.points.size > 1) {
                    val paint = Paint().apply {
                        color = paintPath.color.toArgb()
                        strokeWidth = paintPath.strokeWidth
                        style = Paint.Style.STROKE
                        strokeCap = when (paintPath.strokeCap) {
                            StrokeCap.Round -> Paint.Cap.ROUND
                            StrokeCap.Square -> Paint.Cap.SQUARE
                            else -> Paint.Cap.BUTT
                        }
                        strokeJoin = when (paintPath.strokeJoin) {
                            StrokeJoin.Round -> Paint.Join.ROUND
                            StrokeJoin.Bevel -> Paint.Join.BEVEL
                            else -> Paint.Join.MITER
                        }
                        isAntiAlias = true
                    }

                    val path = Path()
                    path.moveTo(paintPath.points.first().x, paintPath.points.first().y)

                    for (i in 1 until paintPath.points.size) {
                        val from = paintPath.points[i - 1]
                        val to = paintPath.points[i]

                        if (i < paintPath.points.size - 1) {
                            val nextPoint = paintPath.points[i + 1]
                            val controlX = to.x
                            val controlY = to.y
                            val endX = (to.x + nextPoint.x) / 2
                            val endY = (to.y + nextPoint.y) / 2
                            path.quadTo(controlX, controlY, endX, endY)
                        } else {
                            path.lineTo(to.x, to.y)
                        }
                    }

                    drawPath(path, paint)
                }
            }

        }
    }

    private fun normalizeAndDrawExamplePaths(
        canvas: Canvas,
        parsedPath: ParsedPath,
        bounds: RectF,
        canvasSize: Int,
        strokeWidthMultiplier: Float
    ) {
        if (bounds.isEmpty) return

        canvas.withSave {

            // 2c. Offset to (0,0)
            // 2d. Scale to fit canvas while preserving aspect ratio
            val scaleX = canvasSize / bounds.width()
            val scaleY = canvasSize / bounds.height()
            val scale = min(scaleX, scaleY)

            // Center the drawing
            val scaledWidth = bounds.width() * scale
            val scaledHeight = bounds.height() * scale
            val translateX = (canvasSize - scaledWidth) / 2f - bounds.left * scale
            val translateY = (canvasSize - scaledHeight) / 2f - bounds.top * scale

            translate(translateX, translateY)
            scale(scale, scale)

            // Draw each path with thicker stroke (as specified in step 1)
            for (pathData in parsedPath.paths) {
                val path = PathParser.createPathFromPathData(pathData.pathData)
                if (path != null) {
                    val paint = Paint().apply {
                        try {
                            color = Color(pathData.strokeColor.toColorInt()).toArgb()
                        } catch (e: Exception) {
                            color = Color.Black.toArgb() // Fallback color
                        }
                        // 1. Apply the stroke width multiplier based on difficulty
                        strokeWidth = pathData.strokeWidth * strokeWidthMultiplier
                        style = Paint.Style.STROKE
                        strokeCap = Paint.Cap.ROUND
                        strokeJoin = Paint.Join.ROUND
                        isAntiAlias = true
                    }

                    drawPath(path, paint)
                }
            }

        }
    }

}