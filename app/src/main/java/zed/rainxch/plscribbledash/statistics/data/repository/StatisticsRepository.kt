package zed.rainxch.plscribbledash.statistics.data.repository

import zed.rainxch.plscribbledash.statistics.presentation.models.Statistic

interface StatisticsRepository {
    suspend fun getStatistics(): List<Statistic>
    suspend fun updateStatistic(statistic: Statistic)
}