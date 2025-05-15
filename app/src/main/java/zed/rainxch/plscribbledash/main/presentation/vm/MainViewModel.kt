package zed.rainxch.plscribbledash.main.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.main.domain.InitializePlayerUseCase
import zed.rainxch.plscribbledash.main.domain.InitializeStatisticsUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val initializeStatisticsUseCase: InitializeStatisticsUseCase,
    private val initializeShopUseCase: InitializeStatisticsUseCase,
    private val initializePlayerUseCase: InitializePlayerUseCase,
) : ViewModel() {

    init {
        initializeStatistics()
        initializeShop()
        initializePlayer()
    }

    fun initializeStatistics() {
        viewModelScope.launch {
            initializeStatisticsUseCase()
        }
    }

    fun initializeShop() {
        viewModelScope.launch {
            initializeShopUseCase()
        }
    }

    fun initializePlayer() {
        viewModelScope.launch {
            initializePlayerUseCase()
        }
    }

}