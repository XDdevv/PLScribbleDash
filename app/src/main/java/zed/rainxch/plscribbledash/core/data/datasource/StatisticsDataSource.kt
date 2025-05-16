package zed.rainxch.plscribbledash.core.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import zed.rainxch.plscribbledash.core.data.utils.constants.Constants
import zed.rainxch.plscribbledash.core.data.db.dao.StatisticsDao
import zed.rainxch.plscribbledash.core.data.db.entity.StatisticEntity
import javax.inject.Inject

class StatisticsDataSource @Inject constructor(
    private val statisticsDao: StatisticsDao
) {
    suspend fun getStatistic(id: String): StatisticEntity = coroutineScope() {
        withContext(Dispatchers.Default) {
            statisticsDao.getStatistics().find { it.id == id }
                ?: StatisticEntity(Constants.DB_STATS_MEH_PLUS, 0)
        }
    }

    suspend fun updateStatistic(id: String, quantity: Int) = coroutineScope {
        withContext(Dispatchers.Default) {
            val statisticEntity =
                statisticsDao.getStatisticById(id).copy(quantity = quantity)
            statisticsDao.updateStatistic(statisticEntity)
        }
    }

    suspend fun checkIfStatisticsAvailable(): Boolean = coroutineScope {
        statisticsDao.getStatisticsCount() > 0
    }

    suspend fun insertAllStatistics() = coroutineScope {
        val list = listOf<StatisticEntity>(
            StatisticEntity(
                id = Constants.DB_STATS_HIGHEST_SPEED_ACCURACY,
                quantity = 0,
            ),
            StatisticEntity(
                id = Constants.DB_STATS_MEH_PLUS,
                quantity = 0,
            ),
            StatisticEntity(
                id = Constants.DB_STATS_HIGHEST_ENDLESS_ACCURACY,
                quantity = 0,
            ),
            StatisticEntity(
                id = Constants.DB_STATS_MOST_ENDLESS_PLAYED_PLUS,
                quantity = 0,
            )
        )
        statisticsDao.insertStatistics(list)
    }
}