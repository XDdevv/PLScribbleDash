package zed.rainxch.plscribbledash.home.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ParsedPath(
    val paths: List<PathData>,
    val width: Int,
    val height: Int,
    val viewportWidth: Float,
    val viewportHeight: Float
)
