package zed.rainxch.plscribbledash.presentation.screens.statistics.vm

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.domain.repository.StatisticsRepository
import zed.rainxch.plscribbledash.presentation.core.model.Statistic
import zed.rainxch.plscribbledash.presentation.core.model.StatisticsType
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