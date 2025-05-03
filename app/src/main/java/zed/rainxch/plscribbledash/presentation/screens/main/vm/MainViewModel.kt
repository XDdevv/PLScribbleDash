package zed.rainxch.plscribbledash.presentation.screens.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.domain.use_case.CreateStatisticsUseCase
import zed.rainxch.plscribbledash.presentation.core.navigation.NavGraph
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createStatisticsUseCase: CreateStatisticsUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            createStatisticsUseCase()
        }
    }

    fun bottomNavAllowedScreenList(): List<NavGraph> {
        return listOf(
            NavGraph.Home,
            NavGraph.Statistics,
        )
    }

}