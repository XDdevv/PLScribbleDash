package zed.rainxch.plscribbledash.home.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import zed.rainxch.plscribbledash.home.domain.model.PaintPath

@Composable
fun DrawingCanvas(
    paths: List<PaintPath>,
    currentPath: PaintPath?,
    onTouchStart : (Offset) -> Unit,
    onTouchMove : (Offset) -> Unit,
    onTouchEnd : () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled : Boolean = true
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

                drawPath(
                    path = path,
                    color = paintPath.color,
                    style = Stroke(
                        width = paintPath.strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
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

                drawPath(
                    path = path,
                    color = paintPath.color,
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