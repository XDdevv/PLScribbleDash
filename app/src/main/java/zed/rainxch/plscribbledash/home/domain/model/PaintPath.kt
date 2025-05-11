package zed.rainxch.plscribbledash.home.domain.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.core.graphics.PathParser.createPathFromPathData
import java.util.UUID

data class PaintPath(
    val id: String = UUID.randomUUID().toString(),
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float,
    val strokeCap: StrokeCap = StrokeCap.Round,
    val strokeJoin: StrokeJoin = StrokeJoin.Round
)

fun String.toPaintPath(): PaintPath {
    val parts = split("|")
    val pointsStr = parts[0]
    val colorStr = parts[1]

    val points = pointsStr.split(";").map {
        val (x, y) = it.split(",").map { it.toFloat() }
        Offset(x, y)
    }

    val (r, g, b, a) = colorStr.split(",").map { it.toFloat() }
    val color = Color(r, g, b, a)

    val cap = StrokeCap.Round
    val join = StrokeJoin.Round

    return PaintPath(UUID.randomUUID().toString(), points, color, 10f, cap, join)
}

fun PaintPath.toStringDTO(): String {
    val pointsStr = points.joinToString(";") { "${it.x},${it.y}" }
    val colorStr = "${color.red},${color.green},${color.blue},${color.alpha}"
    val pathData = listOf(pointsStr, colorStr).joinToString("|")
    return pathData
}


fun PathData.toPath(): Path {
    return createPathFromPathData(this.pathData).asComposePath()
}