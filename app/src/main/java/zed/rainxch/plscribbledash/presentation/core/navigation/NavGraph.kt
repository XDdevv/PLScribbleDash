package zed.rainxch.plscribbledash.presentation.core.navigation

import kotlinx.serialization.Serializable
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.presentation.core.model.GameModeOptions
import zed.rainxch.plscribbledash.presentation.core.model.PaintPathDTO

@Serializable
sealed class NavGraph(var route: String) {

    @Serializable
    object Home : NavGraph("home")

    @Serializable
    object Statistics : NavGraph("statistics")

    @Serializable
    data class GameModeScreen(
        var gameMode: GameModeOptions
    ) : NavGraph("gameModeScreen")

    @Serializable
    data class GameScreen (
        var difficultyLevel: DifficultyLevelOptions,
    ) : NavGraph("gameScreen")

    @Serializable
    data class ResultScreen(
        val rate: Int,
        val previewDrawing: String,
        val userDrawn: List<String>
    ) : NavGraph("resultScreen")

}