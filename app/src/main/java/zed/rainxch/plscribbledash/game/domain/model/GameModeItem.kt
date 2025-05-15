package zed.rainxch.plscribbledash.game.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class GameModeItem(
    val title: String,
    @DrawableRes val image: Int,
    val gameMode: GameModeOptions,
    val borderColor: Color
)