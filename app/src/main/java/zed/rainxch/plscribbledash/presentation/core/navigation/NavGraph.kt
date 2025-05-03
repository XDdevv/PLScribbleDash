package zed.rainxch.plscribbledash.presentation.core.navigation

import kotlinx.serialization.Serializable
import zed.rainxch.plscribbledash.domain.model.ParsedPath
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.presentation.core.model.GameModeOptions
import zed.rainxch.plscribbledash.presentation.screens.result.utils.ResultState

@Serializable
sealed class NavGraph(var route: String) {

    @Serializable
    object Home : NavGraph("home")

    @Serializable
    object Statistics : NavGraph("statistics")

    @Serializable
    data class DifficultyModeScreen(
        var gameMode: GameModeOptions
    ) : NavGraph("gameModeScreen")

    @Serializable
    data class OneRoundWonderGameScreen(
        var difficultyLevel: DifficultyLevelOptions
    ) : NavGraph("oneRoundWonderGameScreen")

    @Serializable
    data class SpeedDrawGameScreen(
        var difficultyLevel: DifficultyLevelOptions
    ) : NavGraph("speedDrawGameScreen")

    @Serializable
    data class EndlessModeGameScreen(
        var difficultyLevel: DifficultyLevelOptions
    ) : NavGraph("endlessModeGameScreen")

    @Serializable
    data class ResultScreen(
        val resultState: ResultState
    ) : NavGraph("resultScreen")

}