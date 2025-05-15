package zed.rainxch.plscribbledash.game.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PathData(
    val pathData: String,
    val strokeWidth: Float,
    val fillColor: String,
    val strokeColor: String
)
