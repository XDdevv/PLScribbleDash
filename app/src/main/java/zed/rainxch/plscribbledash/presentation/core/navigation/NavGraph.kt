package zed.rainxch.plscribbledash.presentation.core.navigation

import kotlinx.serialization.Serializable
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelItem
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.presentation.core.model.GameModeOptions

@Serializable
sealed class NavGraph(var route: String) {

    @Serializable
    object Home : NavGraph("home")

    @Serializable
    object Test : NavGraph("test")

    override fun toString(): String {
        return route
    }

    @Serializable
    data class GameModeScreen(
        var gameMode: GameModeOptions
    ) : NavGraph("gameModeScreen")

    @Serializable
    data class GameScreen (
        var difficultyLevel: DifficultyLevelOptions,
//        var gameMode: GameModeOptions
    ) : NavGraph("gameScreen")
}