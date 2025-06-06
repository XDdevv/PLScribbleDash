package zed.rainxch.plscribbledash.statistics.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.statistics.presentation.models.Statistic
import zed.rainxch.plscribbledash.statistics.data.repository.StatisticsRepository
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    private val _statisticsState = MutableStateFlow<List<Statistic>>(emptyList())
    val statisticsState = _statisticsState.asStateFlow()

    init {
        getStatistics()
    }

    fun getStatistics() {
        viewModelScope.launch {
            _statisticsState.emit(statisticsRepository.getStatistics())
        }
    }
}