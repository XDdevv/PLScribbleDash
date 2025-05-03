package zed.rainxch.plscribbledash.data.repository

import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.constants.Constants
import zed.rainxch.plscribbledash.data.datasource.StatisticsDataSource
import zed.rainxch.plscribbledash.domain.repository.StatisticsRepository
import zed.rainxch.plscribbledash.presentation.core.model.Statistic
import zed.rainxch.plscribbledash.presentation.core.model.StatisticsType
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val statisticsDataSource: StatisticsDataSource
) : StatisticsRepository {
    override suspend fun getStatistics(): List<Statistic> {
        return listOf(
            Statistic(
                id = Constants.DB_STATS_HIGHEST_SPEED_ACCURACY,
                icon = R.drawable.ic_time,
                title = "Highest Speed Draw accuracy %",
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_HIGHEST_SPEED_ACCURACY).quantity.toFloat(),
                statisticsType = StatisticsType.PERCENTAGE
            ),
            Statistic(
                id = Constants.DB_STATS_MEH_PLUS,
                icon = R.drawable.ic_flash,
                title = "Most Meh+ drawings in Speed Draw",
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_MEH_PLUS).quantity.toFloat(),
                statisticsType = StatisticsType.COUNT
            ),
            Statistic(
                id = Constants.DB_STATS_HIGHEST_ENDLESS_ACCURACY,
                icon = R.drawable.ic_star,
                title = "Highest Endless Mode accuracy %",
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_HIGHEST_ENDLESS_ACCURACY).quantity.toFloat(),
                statisticsType = StatisticsType.PERCENTAGE
            ),
            Statistic(
                id = Constants.DB_STATS_MOST_ENDLESS_PLAYED_PLUS,
                icon = R.drawable.ic_master,
                title = "Most drawings completed in Endless Mode",
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_MOST_ENDLESS_PLAYED_PLUS).quantity.toFloat(),
                statisticsType = StatisticsType.COUNT
            )
        )
    }

    override suspend fun updateStatistic(statistic: Statistic) {
        statisticsDataSource.updateStatistic(statistic.id, statistic.progress.toInt())
    }
}