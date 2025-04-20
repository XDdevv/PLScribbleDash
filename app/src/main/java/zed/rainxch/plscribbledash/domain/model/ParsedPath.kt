package zed.rainxch.plscribbledash.domain.model

data class ParsedPath(
    val paths: List<PathData>,
    val width: Int,
    val height: Int,
    val viewportWidth: Float,
    val viewportHeight: Float
)
