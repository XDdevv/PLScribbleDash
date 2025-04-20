package zed.rainxch.plscribbledash.presentation.core.model

import kotlinx.serialization.Serializable

@Serializable
data class PaintPathDTO(
    val id: String,
    val pathData: String,
)
