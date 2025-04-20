package zed.rainxch.plscribbledash.presentation.core.model

data class Statistic(
    val id: String,
    val icon: Int,
    val title: String,
    val progress: Float,
    val statisticsType: StatisticsType
)