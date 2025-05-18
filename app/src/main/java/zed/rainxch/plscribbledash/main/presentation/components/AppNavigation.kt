package zed.rainxch.plscribbledash.main.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.serializer
import zed.rainxch.plscribbledash.core.presentation.navigation.NavGraph
import zed.rainxch.plscribbledash.core.presentation.navigation.SerializableNavType
import zed.rainxch.plscribbledash.game.domain.model.GameModeOptions
import zed.rainxch.plscribbledash.game.presentation.difficulty_mode.DifficultyModeScreen
import zed.rainxch.plscribbledash.game.presentation.game.endless.EndlessGameScreen
import zed.rainxch.plscribbledash.game.presentation.game.one_round.OneRoundGameScreen
import zed.rainxch.plscribbledash.game.presentation.game.speed_draw.SpeedDrawGameScreen
import zed.rainxch.plscribbledash.game.presentation.home.HomeScreen
import zed.rainxch.plscribbledash.game.presentation.result.ResultScreen
import zed.rainxch.plscribbledash.game.presentation.result.utils.ResultState
import zed.rainxch.plscribbledash.shop.presentation.ShopScreen
import zed.rainxch.plscribbledash.statistics.presentation.StatisticsScreen
import kotlin.reflect.typeOf

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    snackState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = NavGraph.ShopScreen,
        modifier = modifier.fillMaxSize()
    ) {
        composable<NavGraph.HomeScreen> {
            HomeScreen(navController)
        }

        composable<NavGraph.StatisticsScreen> {
            StatisticsScreen()
        }

        composable<NavGraph.ShopScreen> {
            ShopScreen(snackState = snackState)
        }

        composable<NavGraph.DifficultyModeScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<NavGraph.DifficultyModeScreen>()
            DifficultyModeScreen(
                gameMode = args.gameMode,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onDifficultyLevelItemSelected = { difficultyLevelOption, gameModeOption ->
                    when (gameModeOption) {
                        GameModeOptions.OneRoundWonder -> {
                            navController.navigate(
                                NavGraph.OneRoundWonderGameScreen(
                                    difficultyLevel = difficultyLevelOption,
                                )
                            )
                        }

                        GameModeOptions.SpeedDraw -> {
                            navController.navigate(
                                NavGraph.SpeedDrawGameScreen(
                                    difficultyLevel = difficultyLevelOption,
                                )
                            )
                        }

                        GameModeOptions.EndlessMode -> {
                            navController.navigate(
                                NavGraph.EndlessModeGameScreen(
                                    difficultyLevel = difficultyLevelOption,
                                )
                            )
                        }
                    }

                }
            )
        }

        composable<NavGraph.OneRoundWonderGameScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<NavGraph.OneRoundWonderGameScreen>()
            OneRoundGameScreen(navController, args.difficultyLevel)
        }

        composable<NavGraph.SpeedDrawGameScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<NavGraph.OneRoundWonderGameScreen>()
            SpeedDrawGameScreen(navController, args.difficultyLevel)
        }

        composable<NavGraph.EndlessModeGameScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<NavGraph.OneRoundWonderGameScreen>()
            EndlessGameScreen(navController, args.difficultyLevel)
        }

        composable<NavGraph.ResultScreen>(
            typeMap = mapOf(
                typeOf<ResultState>() to SerializableNavType.create(serializer<ResultState>())
            )
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<NavGraph.ResultScreen>()
            ResultScreen(
                navController = navController,
                resultState = args.resultState
            )
        }
    }
}
