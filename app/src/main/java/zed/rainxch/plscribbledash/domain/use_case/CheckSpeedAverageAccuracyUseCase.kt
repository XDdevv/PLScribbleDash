package zed.rainxch.plscribbledash.domain.use_case

import zed.rainxch.plscribbledash.core.constants.Constants
import zed.rainxch.plscribbledash.domain.repository.StatisticsRepository
import javax.inject.Inject

class CheckSpeedAverageAccuracyUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(
        score: Int
    ): Boolean {
        val statistic =
            statisticsRepository.getStatistics().find { it.id == Constants.DB_STATS_HIGHEST_SPEED_ACCURACY }
        val isGreater = (statistic?.progress ?: 0f) < score.toFloat()
        if (isGreater && statistic != null) {
            statisticsRepository.updateStatistic(statistic.copy(progress = score.toFloat()))
        }
        return isGreater
    }
}