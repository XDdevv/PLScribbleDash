package zed.rainxch.plscribbledash.statistics.domain.models

import androidx.compose.ui.graphics.Color

data class Statistic(
    val id: String,
    val icon: Int,
    val title: String,
    val progress: Float,
    val dominantColor: Color = Color.Gray,
    val statisticsType: StatisticsType
)