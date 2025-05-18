package zed.rainxch.plscribbledash.game.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen
import zed.rainxch.plscribbledash.game.domain.model.PaintPath

@Composable
fun DrawingCanvas(
    paths: List<PaintPath>,
    currentPath: PaintPath?,
    onTouchStart : (Offset) -> Unit,
    onTouchMove : (Offset) -> Unit,
    onTouchEnd : () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled : Boolean = true,
    shopPen: ShopPen,
    shopCanvas: ShopCanvas
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
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