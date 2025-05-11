package zed.rainxch.plscribbledash.statistics.data.repository

import zed.rainxch.plscribbledash.statistics.domain.models.Statistic

interface StatisticsRepository {
    suspend fun getStatistics(): List<Statistic>
    suspend fun updateStatistic(statistic: Statistic)
}