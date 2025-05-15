package zed.rainxch.plscribbledash.game.presentation.game.speed_draw.utils

sealed class SpeedGameState {
    data object PREVIEW : SpeedGameState()

    data object PLAY : SpeedGameState()

    data object CONTINUE : SpeedGameState()

    data class FINISHED(
        val averageScore: Int,
        val mehPlusCount: Int,
        val isMehPlusHighScore: Boolean,
        val isAverageAccuracyHighScore: Boolean
    ) : SpeedGameState()
}