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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath
import zed.rainxch.plscribbledash.game.domain.model.toPath

@Composable
fun PreviewPathCanvas(
    modifier: Modifier = Modifier.size(350.dp),
    parsedPath: ParsedPath?,
    backgroundColor: Color = Color.White,
    drawGrid: Boolean = true
) {
    Canvas(
        modifier = modifier
            .background(backgroundColor)
            .clip(RoundedCornerShape(18.dp))
            .clipToBounds()
            .then(if (drawGrid) Modifier.drawGridLines() else Modifier)
    ) {
        parsedPath?.let { pathData ->
            val canvasWidth = size.width
            val canvasHeight = size.height

            val viewportWidth = pathData.viewportWidth.takeIf { it > 0 }?.toFloat() ?: return@Canvas
            val viewportHeight = pathData.viewportHeight.takeIf { it > 0 }?.toFloat() ?: return@Canvas

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
                pathData.paths.forEachIndexed { index, modelPath ->
                    val path = modelPath.toPath()
                    drawPath(
                        path = path,
                        color = Color.Black,
                        style = Stroke(width = modelPath.strokeWidth)
                    )
                }
            }
        }
    }
}
