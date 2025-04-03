package zed.rainxch.plscribbledash.domain.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import java.util.UUID


data class PaintPath(
    val id: String = UUID.randomUUID().toString(),
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float,
    val strokeCap: StrokeCap = StrokeCap.Round,
    val strokeJoin: StrokeJoin = StrokeJoin.Round
)
