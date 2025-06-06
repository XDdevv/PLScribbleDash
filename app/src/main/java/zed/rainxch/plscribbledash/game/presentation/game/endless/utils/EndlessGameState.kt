package zed.rainxch.plscribbledash.game.presentation.game.endless.utils

import zed.rainxch.plscribbledash.game.domain.model.PaintPath
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath

sealed class EndlessGameState {
    data object PREVIEW : EndlessGameState()

    data object PLAY : EndlessGameState()

    data class RESULT(
        val score: Int,
        val previewPaths: ParsedPath,
        val userDrawnPath: List<PaintPath>,
        val gainedCoins: Int
    ) : EndlessGameState()

    data class FINISHED(
        val averageScore: Int,
        val mehPlusCount: Int,
        val isMehPlusHighScore: Boolean,
        val isAverageAccuracyHighScore: Boolean,
        val coins: Int
    ) : EndlessGameState()
}