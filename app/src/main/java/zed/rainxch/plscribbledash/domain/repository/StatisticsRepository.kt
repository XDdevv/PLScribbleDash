package zed.rainxch.plscribbledash.domain.repository

import zed.rainxch.plscribbledash.presentation.core.model.Statistic

interface StatisticsRepository {
    suspend fun getStatistics(): List<Statistic>
    suspend fun updateStatistic(statistic: Statistic)
}