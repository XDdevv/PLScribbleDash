package zed.rainxch.plscribbledash.game.domain.use_case

import zed.rainxch.plscribbledash.core.data.utils.constants.Constants
import zed.rainxch.plscribbledash.statistics.data.repository.StatisticsRepository
import javax.inject.Inject

class CheckGoodPlusScoreUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(
        score: Int
    ): Boolean {
        val statistic =
            statisticsRepository.getStatistics().find { it.id == Constants.DB_STATS_MOST_ENDLESS_PLAYED_PLUS }
        val isGreater = (statistic?.progress ?: 0f) < score.toFloat()
        if (isGreater && statistic != null) {
            statisticsRepository.updateStatistic(statistic.copy(progress = score.toFloat()))
        }
        return isGreater
    }
}