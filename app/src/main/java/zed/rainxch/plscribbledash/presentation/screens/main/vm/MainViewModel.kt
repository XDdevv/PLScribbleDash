package zed.rainxch.plscribbledash.presentation.screens.main.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import zed.rainxch.plscribbledash.presentation.core.navigation.NavGraph
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    fun bottomNavAllowedScreenList(): List<NavGraph> {
        return listOf(
            NavGraph.Home,
            NavGraph.Test,
        )
    }

}