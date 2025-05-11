package zed.rainxch.plscribbledash.home.data.datasource

import androidx.compose.ui.graphics.Color
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.home.domain.model.GameModeOptions
import zed.rainxch.plscribbledash.home.domain.model.GameModeItem

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