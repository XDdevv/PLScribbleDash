package zed.rainxch.plscribbledash.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.home.domain.use_case.CreateStatisticsUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createStatisticsUseCase: CreateStatisticsUseCase
) : ViewModel() {

    fun initializeStatistics() {
        viewModelScope.launch {
            createStatisticsUseCase()
        }
    }

}