package zed.rainxch.plscribbledash.presentation.core.model

import zed.rainxch.plscribbledash.domain.model.ParsedPath

sealed class GameState {
    data object PREVIEW : GameState()
    data object PLAY : GameState()
    data class FINISHED(val score: Int, val path: ParsedPath) : GameState()
}