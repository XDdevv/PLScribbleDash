package zed.rainxch.plscribbledash.presentation.screens.game_modes.one_round.utils

import zed.rainxch.plscribbledash.domain.model.ParsedPath

sealed class OneRoundGameState {
    data object PREVIEW : OneRoundGameState()
    data object PLAY : OneRoundGameState()
    data class FINISHED(val score: Int, val path: ParsedPath) : OneRoundGameState()
}