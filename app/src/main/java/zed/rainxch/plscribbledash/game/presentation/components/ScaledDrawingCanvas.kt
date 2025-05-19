package zed.rainxch.plscribbledash.game.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen
import zed.rainxch.plscribbledash.game.domain.model.PaintPath

@Composable
fun ScaledDrawingCanvas(
    modifier: Modifier = Modifier.size(350.dp),
    viewportWidth: Float = 1000f,
    viewportHeight: Float = 1000f,
    userPaths: List<PaintPath>,
    drawBackground: Boolean = true,
    drawGrid: Boolean = true,
    canvasColor: ShopCanvas,
    shopPen: ShopPen
) {
    when (val state = canvasColor) {
        is ShopCanvas.Basic, is ShopCanvas.Premium -> {
            Canvas(
                modifier = modifier
                    .clip(RoundedCornerShape(18.dp))
                    .then(if (drawBackground) Modifier.background(state.bColor) else Modifier)
                    .clipToBounds()
                    .then(if (drawGrid) Modifier.drawGridLines() else Modifier)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val scaleX = canvasWidth / viewportWidth
                val scaleY = canvasHeight / viewportHeight
                val scale = minOf(scaleX, scaleY)

                val scaledWidth = viewportWidth * scale
                val scaledHeight = viewportHeight * scale
                val translateX = (canvasWidth - scaledWidth) / 2f
                val translateY = (canvasHeight - scaledHeight) / 2f

                withTransform({
                    translate(left = translateX, top = translateY)
                    scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
                }) {
                    userPaths.forEach { paintPath ->
                        if (paintPath.points.size > 1) {
                            val path = Path().apply {
                                moveTo(paintPath.points.first().x, paintPath.points.first().y)

                                for (i in 1 until paintPath.points.size) {
                                    val to = paintPath.points[i]

                                    if (i < paintPath.points.size - 1) {
                                        val next = paintPath.points[i + 1]
                                        quadraticTo(
                                            to.x, to.y,
                                            (to.x + next.x) / 2, (to.y + next.y) / 2
                                        )
                                    } else {
                                        lineTo(to.x, to.y)
                                    }
                                }
                            }

                            when (val state = shopPen) {
                                is ShopPen.Basic, is ShopPen.Premium -> {
                                    drawPath(
                                        path = path,
                                        color = state.penColor,
                                        style = Stroke(
                                            width = paintPath.strokeWidth,
                                            cap = StrokeCap.Round,
                                            join = StrokeJoin.Round
                                        )
                                    )
                                }

                                is ShopPen.Legendary -> {
                                    drawPath(
                                        path = path,
                                        brush = state.brush(),
                                        style = Stroke(
                                            width = paintPath.strokeWidth,
                                            cap = StrokeCap.Round,
                                            join = StrokeJoin.Round
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        is ShopCanvas.Legendary -> {
            val imageBitmap = ImageBitmap.imageResource(state.imageRes)

            Canvas(
                modifier = modifier
                    .clip(RoundedCornerShape(18.dp))
                    .clipToBounds()
                    .then(if (drawGrid) Modifier.drawGridLines() else Modifier)
            ) {
                drawImage(
                    imageBitmap,
                    topLeft = Offset(0f,0f)
                )
                drawGridLines()

                val canvasWidth = size.width
                val canvasHeight = size.height

                val scaleX = canvasWidth / viewportWidth
                val scaleY = canvasHeight / viewportHeight
                val scale = minOf(scaleX, scaleY)

                val scaledWidth = viewportWidth * scale
                val scaledHeight = viewportHeight * scale
                val translateX = (canvasWidth - scaledWidth) / 2f
                val translateY = (canvasHeight - scaledHeight) / 2f

                withTransform({
                    translate(left = translateX, top = translateY)
                    scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
                }) {
                    userPaths.forEach { paintPath ->
                        if (paintPath.points.size > 1) {
                            val path = Path().apply {
                                moveTo(paintPath.points.first().x, paintPath.points.first().y)

                                for (i in 1 until paintPath.points.size) {
                                    val to = paintPath.points[i]

                                    if (i < paintPath.points.size - 1) {
                                        val next = paintPath.points[i + 1]
                                        quadraticTo(
                                            to.x, to.y,
                                            (to.x + next.x) / 2, (to.y + next.y) / 2
                                        )
                                    } else {
                                        lineTo(to.x, to.y)
                                    }
                                }
                            }

                            when (val state = shopPen) {
                                is ShopPen.Basic, is ShopPen.Premium -> {
                                    drawPath(
                                        path = path,
                                        color = state.penColor,
                                        style = Stroke(
                                            width = paintPath.strokeWidth,
                                            cap = StrokeCap.Round,
                                            join = StrokeJoin.Round
                                        )
                                    )
                                }

                                is ShopPen.Legendary -> {
                                    drawPath(
                                        path = path,
                                        brush = state.brush(),
                                        style = Stroke(
                                            width = paintPath.strokeWidth,
                                            cap = StrokeCap.Round,
                                            join = StrokeJoin.Round
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
