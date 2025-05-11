package zed.rainxch.plscribbledash.core.presentation.navigation

import kotlinx.serialization.Serializable
import zed.rainxch.plscribbledash.home.domain.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.home.domain.model.GameModeOptions
import zed.rainxch.plscribbledash.home.presentation.result.utils.ResultState

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