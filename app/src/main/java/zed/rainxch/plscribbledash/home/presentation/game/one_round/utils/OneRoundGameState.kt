package zed.rainxch.plscribbledash.home.presentation.game.one_round.utils

import zed.rainxch.plscribbledash.home.domain.model.ParsedPath

sealed class OneRoundGameState {
    data object PREVIEW : OneRoundGameState()
    data object PLAY : OneRoundGameState()
    data class FINISHED(val score: Int, val path: ParsedPath) : OneRoundGameState()
}