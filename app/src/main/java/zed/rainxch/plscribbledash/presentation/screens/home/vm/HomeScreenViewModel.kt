package zed.rainxch.plscribbledash.presentation.screens.home.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.presentation.core.model.GameModeItem
import zed.rainxch.plscribbledash.presentation.core.model.GameModeOptions
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    fun getGameModes(): List<GameModeItem> {
        return listOf(
            GameModeItem(
                "One round wonder",
                R.drawable.ic_one_round_wonder,
                GameModeOptions.OneRoundWonder
            ),
        )
    }
}