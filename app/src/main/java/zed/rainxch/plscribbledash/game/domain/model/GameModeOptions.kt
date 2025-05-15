package zed.rainxch.plscribbledash.game.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class GameModeOptions {
    OneRoundWonder,
    SpeedDraw,
    EndlessMode
}