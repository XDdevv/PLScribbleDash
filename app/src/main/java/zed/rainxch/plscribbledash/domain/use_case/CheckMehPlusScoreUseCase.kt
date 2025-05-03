package zed.rainxch.plscribbledash.domain.use_case

import zed.rainxch.plscribbledash.core.constants.Constants
import zed.rainxch.plscribbledash.domain.repository.StatisticsRepository
import zed.rainxch.plscribbledash.presentation.core.model.Statistic
import javax.inject.Inject

class CheckMehPlusScoreUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(
        score: Int
    ): Boolean {
        val statistic =
            statisticsRepository.getStatistics().find { it.id == Constants.DB_STATS_MEH_PLUS }
        val isGreater = (statistic?.progress ?: 0f) < score.toFloat()
        if (isGreater && statistic != null) {
            statisticsRepository.updateStatistic(statistic.copy(progress = score.toFloat()))
        }
        return isGreater
    }
}