package zed.rainxch.plscribbledash.statistics.domain.repository

import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.data.constants.Constants
import zed.rainxch.plscribbledash.statistics.data.datasource.StatisticsDataSource
import zed.rainxch.plscribbledash.statistics.data.repository.StatisticsRepository
import zed.rainxch.plscribbledash.statistics.domain.models.Statistic
import zed.rainxch.plscribbledash.statistics.domain.models.StatisticsType
import zed.rainxch.plscribbledash.statistics.presentation.utils.DominantColorExtractor
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val statisticsDataSource: StatisticsDataSource,
    private val dominantColorExtractor: DominantColorExtractor
) : StatisticsRepository {
    override suspend fun getStatistics(): List<Statistic> {
        return listOf(
            Statistic(
                id = Constants.DB_STATS_HIGHEST_SPEED_ACCURACY,
                icon = R.drawable.ic_time,
                title = "Highest Speed Draw accuracy %",
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_HIGHEST_SPEED_ACCURACY).quantity.toFloat(),
                dominantColor = dominantColorExtractor.extractDominantColorFromImage(R.drawable.ic_time),
                statisticsType = StatisticsType.PERCENTAGE
            ),
            Statistic(
                id = Constants.DB_STATS_MEH_PLUS,
                icon = R.drawable.ic_flash,
                title = "Most Meh+ drawings in Speed Draw",
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_MEH_PLUS).quantity.toFloat(),
                dominantColor = dominantColorExtractor.extractDominantColorFromImage(R.drawable.ic_flash),
                statisticsType = StatisticsType.COUNT
            ),
            Statistic(
                id = Constants.DB_STATS_HIGHEST_ENDLESS_ACCURACY,
                icon = R.drawable.ic_star,
                title = "Highest Endless Mode accuracy %",
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_HIGHEST_ENDLESS_ACCURACY).quantity.toFloat(),
                dominantColor = dominantColorExtractor.extractDominantColorFromImage(R.drawable.ic_star),
                statisticsType = StatisticsType.PERCENTAGE
            ),
            Statistic(
                id = Constants.DB_STATS_MOST_ENDLESS_PLAYED_PLUS,
                icon = R.drawable.ic_master,
                title = "Most drawings completed in Endless Mode",
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_MOST_ENDLESS_PLAYED_PLUS).quantity.toFloat(),
                dominantColor = dominantColorExtractor.extractDominantColorFromImage(R.drawable.ic_master),
                statisticsType = StatisticsType.COUNT
            )
        )
    }

    override suspend fun updateStatistic(statistic: Statistic) {
        statisticsDataSource.updateStatistic(statistic.id, statistic.progress.toInt())
    }
}