package zed.rainxch.plscribbledash.presentation.screens.game_modes.endless.utils

import zed.rainxch.plscribbledash.domain.model.PaintPath
import zed.rainxch.plscribbledash.domain.model.ParsedPath

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