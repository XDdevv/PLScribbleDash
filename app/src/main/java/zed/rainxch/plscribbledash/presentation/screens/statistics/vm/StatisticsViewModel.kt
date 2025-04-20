package zed.rainxch.plscribbledash.presentation.screens.statistics.vm

import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.presentation.core.model.Statistic
import zed.rainxch.plscribbledash.presentation.core.model.StatisticsType
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(

) : ViewModel() {
    fun getStatistics(): List<Statistic> {
        return listOf(
            Statistic(
                id = "for now 1",
                icon = R.drawable.ic_time,
                title = "Nothing to track...for now",
                progress = 0f,
                statisticsType = StatisticsType.PERCENTAGE
            ),
            Statistic(
                id = "for now 2",
                icon = R.drawable.ic_flash,
                title = "Nothing to track...for now",
                progress = 0f,
                statisticsType = StatisticsType.COUNT
            )
        )
    }
}