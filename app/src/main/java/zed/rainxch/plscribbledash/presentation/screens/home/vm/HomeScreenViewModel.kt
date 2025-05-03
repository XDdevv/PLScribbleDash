package zed.rainxch.plscribbledash.presentation.screens.home.vm

import androidx.compose.ui.graphics.Color
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
                R.drawable.ic_one_round_wonder_mode,
                GameModeOptions.OneRoundWonder,
                Color(0xFF0DD280)
            ),
            GameModeItem(
                "Speed Draw",
                R.drawable.ic_speed_draw_mode,
                GameModeOptions.SpeedDraw,
                Color(0xFF238CFF)
            ),
            GameModeItem(
                "Endless Mode",
                R.drawable.ic_endless_mode,
                GameModeOptions.EndlessMode,
                Color(0xFFFA852C)
            )
        )
    }
}