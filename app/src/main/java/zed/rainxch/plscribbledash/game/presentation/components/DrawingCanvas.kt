package zed.rainxch.plscribbledash.game.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen
import zed.rainxch.plscribbledash.game.domain.model.PaintPath

@Composable
fun DrawingCanvas(
    paths: List<PaintPath>,
    currentPath: PaintPath?,
    onTouchStart: (Offset) -> Unit,
    onTouchMove: (Offset) -> Unit,
    onTouchEnd: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    shopPen: ShopPen,
    shopCanvas: ShopCanvas
) {
    when (val state = shopCanvas) {
        is ShopCanvas.Basic, is ShopCanvas.Premium -> {
            Canvas(
                modifier = modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp))
                    .background(state.bColor)
                    .clipToBounds()
                    .drawGridLines()
                    .pointerInput(Unit) {
                        if (isEnabled) {
                            detectDragGestures(
                                onDragStart = { onTouchStart(it) },
                                onDrag = { it, _ -> onTouchMove(it.position) },
                                onDragEnd = { onTouchEnd() }
                            )
                        }
                    }
            ) {
                paths.forEach { paintPath ->
                    if (paintPath.points.size > 1) {
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
                                path.quadraticBezierTo(controlX, controlY, endX, endY)
                            } else {
                                path.lineTo(to.x, to.y)
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

                // Draw current path
                currentPath?.let { paintPath ->
                    if (paintPath.points.size > 1) {
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
                                path.quadraticBezierTo(controlX, controlY, endX, endY)
                            } else {
                                path.lineTo(to.x, to.y)
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

        is ShopCanvas.Legendary -> {
            val imageBitmap = ImageBitmap.imageResource(state.imageRes)
            Canvas(
                modifier = modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp))
                    .background(state.bColor)
                    .clipToBounds()
                    .pointerInput(Unit) {
                        if (isEnabled) {
                            detectDragGestures(
                                onDragStart = { onTouchStart(it) },
                                onDrag = { it, _ -> onTouchMove(it.position) },
                                onDragEnd = { onTouchEnd() }
                            )
                        }
                    }
            ) {
                drawImage(
                    imageBitmap,
                    topLeft = Offset(0f,0f)
                )
                drawGridLines()
                paths.forEach { paintPath ->
                    if (paintPath.points.size > 1) {
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
                                path.quadraticBezierTo(controlX, controlY, endX, endY)
                            } else {
                                path.lineTo(to.x, to.y)
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

                // Draw current path
                currentPath?.let { paintPath ->
                    if (paintPath.points.size > 1) {
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
                                path.quadraticBezierTo(controlX, controlY, endX, endY)
                            } else {
                                path.lineTo(to.x, to.y)
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

fun DrawScope.drawGridLines() {
    val cellSize = size.width / 3
    for (i in 1..2) {
        drawLine(
            color = Color(0xFFF6F1EC),
            start = Offset(i * cellSize, 0f),
            end = Offset(i * cellSize, size.height),
            strokeWidth = 1.dp.toPx(),
            alpha = .7f
        )
        drawLine(
            color = Color(0xFFF6F1EC),
            start = Offset(0f, i * cellSize),
            end = Offset(size.width, i * cellSize),
            strokeWidth = 1.dp.toPx(),
            alpha = .7f
        )
    }

    val borderWidth = 2.dp.toPx()
    val cornerRadius = 18.dp.toPx()

    drawRoundRect(
        color = Color(0xFFF6F1EC),
        size = Size(size.width, size.height),
        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
        style = Stroke(width = borderWidth)
    )
}