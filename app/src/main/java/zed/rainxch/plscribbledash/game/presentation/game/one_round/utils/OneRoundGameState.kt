package zed.rainxch.plscribbledash.game.presentation.game.one_round.utils

import zed.rainxch.plscribbledash.game.domain.model.ParsedPath

sealed class OneRoundGameState {
    data object PREVIEW : OneRoundGameState()
    data object PLAY : OneRoundGameState()
    data class FINISHED(val score: Int, val path: ParsedPath, val coins: Int) : OneRoundGameState()
}