package zed.rainxch.plscribbledash.statistics.domain.repository

import android.content.Context
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.data.utils.constants.Constants
import zed.rainxch.plscribbledash.core.data.datasource.StatisticsDataSource
import zed.rainxch.plscribbledash.statistics.data.repository.StatisticsRepository
import zed.rainxch.plscribbledash.statistics.presentation.models.Statistic
import zed.rainxch.plscribbledash.statistics.presentation.models.StatisticsType
import zed.rainxch.plscribbledash.statistics.presentation.utils.DominantColorExtractor
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val statisticsDataSource: StatisticsDataSource,
    private val dominantColorExtractor: DominantColorExtractor,
    private val context: Context
) : StatisticsRepository {
    override suspend fun getStatistics(): List<Statistic> {
        return listOf(
            Statistic(
                id = Constants.DB_STATS_HIGHEST_SPEED_ACCURACY,
                icon = R.drawable.ic_time,
                title = context.getString(R.string.highest_speed_draw_accuracy),
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_HIGHEST_SPEED_ACCURACY).quantity.toFloat(),
                dominantColor = dominantColorExtractor.extractDominantColorFromImage(R.drawable.ic_time),
                statisticsType = StatisticsType.PERCENTAGE
            ),
            Statistic(
                id = Constants.DB_STATS_MEH_PLUS,
                icon = R.drawable.ic_flash,
                title = context.getString(R.string.most_meh_drawings_in_speed_draw),
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_MEH_PLUS).quantity.toFloat(),
                dominantColor = dominantColorExtractor.extractDominantColorFromImage(R.drawable.ic_flash),
                statisticsType = StatisticsType.COUNT
            ),
            Statistic(
                id = Constants.DB_STATS_HIGHEST_ENDLESS_ACCURACY,
                icon = R.drawable.ic_star,
                title = context.getString(R.string.highest_endless_mode_accuracy),
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_HIGHEST_ENDLESS_ACCURACY).quantity.toFloat(),
                dominantColor = dominantColorExtractor.extractDominantColorFromImage(R.drawable.ic_star),
                statisticsType = StatisticsType.PERCENTAGE
            ),
            Statistic(
                id = Constants.DB_STATS_MOST_ENDLESS_PLAYED_PLUS,
                icon = R.drawable.ic_palette,
                title = context.getString(R.string.most_drawings_completed_in_endless_mode),
                progress = statisticsDataSource.getStatistic(Constants.DB_STATS_MOST_ENDLESS_PLAYED_PLUS).quantity.toFloat(),
                dominantColor = dominantColorExtractor.extractDominantColorFromImage(R.drawable.ic_palette),
                statisticsType = StatisticsType.COUNT
            )
        )
    }

    override suspend fun updateStatistic(statistic: Statistic) {
        statisticsDataSource.updateStatistic(statistic.id, statistic.progress.toInt())
    }
}