package zed.rainxch.plscribbledash.main.domain

import zed.rainxch.plscribbledash.core.data.datasource.StatisticsDataSource
import javax.inject.Inject

class InitializeShopUseCase @Inject constructor(
    private val dataSource: StatisticsDataSource
) {

    suspend operator fun invoke() {
        if (!dataSource.checkIfStatisticsAvailable()) {
            dataSource.insertAllStatistics()
        }
    }
}