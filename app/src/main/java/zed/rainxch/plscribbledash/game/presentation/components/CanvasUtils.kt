package zed.rainxch.plscribbledash.game.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.drawGridLines() : Modifier {
    return this.then(
        Modifier.drawBehind {
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
    )
}