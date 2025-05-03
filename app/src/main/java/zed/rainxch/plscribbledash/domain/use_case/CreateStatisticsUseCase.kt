package zed.rainxch.plscribbledash.domain.use_case

import zed.rainxch.plscribbledash.data.datasource.StatisticsDataSource
import javax.inject.Inject

class CreateStatisticsUseCase @Inject constructor(
    private val dataSource: StatisticsDataSource
) {

    suspend operator fun invoke() {
        if (!dataSource.checkIfStatisticsAvailable()) {
            dataSource.insertAllStatistics()
        }
    }
}