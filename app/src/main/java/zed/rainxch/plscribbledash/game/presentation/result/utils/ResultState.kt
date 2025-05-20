package zed.rainxch.plscribbledash.game.presentation.result.utils

import kotlinx.serialization.Serializable
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath

@Serializable
sealed class ResultState {
    @Serializable
    data class OneRoundWonder(
        val score: Int,
        val previewPaths: ParsedPath,
        val userDrawnPaths: List<String>,
        val coins: Int
    ) : ResultState()

    @Serializable
    data class SpeedDraw(
        val averageScore: Int,
        val mehPlusCount: Int,
        val isMehPlusHighScore: Boolean,
        val isAverageAccuracyHighScore: Boolean,
        val coins: Int
    ) : ResultState()

    @Serializable
    data class Endless(
        val averageScore: Int,
        val mehPlusCount: Int,
        val isMehPlusHighScore: Boolean,
        val isAverageAccuracyHighScore: Boolean,
        val coins: Int
    ) : ResultState()
}