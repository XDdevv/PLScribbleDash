package zed.rainxch.plscribbledash.home.presentation.game.endless.utils

import zed.rainxch.plscribbledash.home.domain.model.PaintPath
import zed.rainxch.plscribbledash.home.domain.model.ParsedPath

sealed class EndlessGameState {
    data object PREVIEW : EndlessGameState()
    data object PLAY : EndlessGameState()
    data class RESULT(
        val score: Int,
        val previewPaths: ParsedPath,
        val userDrawnPath: List<PaintPath>
    ) :
        EndlessGameState()

    data class FINISHED(
        val averageScore: Int,
        val mehPlusCount: Int,
        val isMehPlusHighScore: Boolean,
        val isAverageAccuracyHighScore: Boolean
    ) : EndlessGameState()
}