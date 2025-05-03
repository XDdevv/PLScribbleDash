package zed.rainxch.plscribbledash.presentation.core.model

import androidx.compose.ui.graphics.Color

data class GameModeItem(
    val title : String,
    val image : Int,
    val gameModeOptions: GameModeOptions,
    val borderColor: Color
)
