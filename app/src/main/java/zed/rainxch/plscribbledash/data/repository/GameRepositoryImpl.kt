package zed.rainxch.plscribbledash.data.repository

import android.content.Context
import org.xmlpull.v1.XmlPullParser
import zed.rainxch.plscribbledash.domain.model.ParsedPath
import zed.rainxch.plscribbledash.domain.repository.GameRepository
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import android.graphics.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.PathParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zed.rainxch.plscribbledash.domain.model.PaintPath
import kotlin.math.min
import androidx.core.graphics.createBitmap
import androidx.core.graphics.withSave
import androidx.core.graphics.toColorInt
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow

class GameRepositoryImpl @javax.inject.Inject constructor(
    private val context: Context
) : GameRepository {
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

        val paths = mutableListOf<zed.rainxch.plscribbledash.domain.model.PathData>()
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
                                    zed.rainxch.plscribbledash.domain.model.PathData(
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
            // 1. Get stroke width multiplier based on difficulty
            val strokeWidthMultiplier = when (difficultyLevelOption) {
                DifficultyLevelOptions.Beginner -> 15f
                DifficultyLevelOptions.Challenging -> 7f
                DifficultyLevelOptions.Master -> 4f
            }

            // Get average stroke widths
            val userStrokeWidth = userPaths.map { it.strokeWidth }.average().toFloat()
            val exampleStrokeWidth =
                exampleParsedPath.paths.map { it.strokeWidth }.average().toFloat()

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

            val userPixels = IntArray(canvasSize * canvasSize)
            val examplePixels = IntArray(canvasSize * canvasSize)

            userBitmap.getPixels(userPixels, 0, canvasSize, 0, 0, canvasSize, canvasSize)
            exampleBitmap.getPixels(examplePixels, 0, canvasSize, 0, 0, canvasSize, canvasSize)

            for (i in userPixels.indices) {
                val userAlpha = android.graphics.Color.alpha(userPixels[i])
                val exampleAlpha = android.graphics.Color.alpha(examplePixels[i])

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
                    }
                }
            }

            // 8. Calculate coverage percentage
            val coveragePercentage = if (visibleUserPixelCount > 0) {
                (matchingUserPixelCount.toFloat() / visibleUserPixelCount.toFloat()) * 100
            } else {
                0f
            }

            // Clean up bitmaps to prevent memory leaks
            userBitmap.recycle()
            exampleBitmap.recycle()

            return@withContext coveragePercentage.toInt()
        }
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

        canvas.save()

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

        canvas.translate(translateX, translateY)
        canvas.scale(scale, scale)

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

                val path = android.graphics.Path()
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

                canvas.drawPath(path, paint)
            }
        }

        canvas.restore()
    }

    private fun normalizeAndDrawExamplePaths(
        canvas: Canvas,
        parsedPath: ParsedPath,
        bounds: RectF,
        canvasSize: Int,
        strokeWidthMultiplier: Float
    ) {
        if (bounds.isEmpty) return

        canvas.save()

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

        canvas.translate(translateX, translateY)
        canvas.scale(scale, scale)

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

                canvas.drawPath(path, paint)
            }
        }

        canvas.restore()
    }
}