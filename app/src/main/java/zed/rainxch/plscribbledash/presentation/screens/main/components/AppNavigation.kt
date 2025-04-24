package zed.rainxch.plscribbledash.presentation.screens.main.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.serializer
import zed.rainxch.plscribbledash.domain.model.ParsedPath
import zed.rainxch.plscribbledash.presentation.core.navigation.NavGraph
import zed.rainxch.plscribbledash.presentation.core.navigation.SerializableNavType
import zed.rainxch.plscribbledash.presentation.screens.game.GameScreen
import zed.rainxch.plscribbledash.presentation.screens.gameMode.GameModeScreen
import zed.rainxch.plscribbledash.presentation.screens.home.HomeScreen
import zed.rainxch.plscribbledash.presentation.screens.result.ResultScreen
import zed.rainxch.plscribbledash.presentation.screens.statistics.StatisticsScreen
import kotlin.reflect.typeOf

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavGraph.Home,
        modifier = modifier.fillMaxSize()
    ) {
        composable<NavGraph.Home> {
            HomeScreen(navController)
        }
        composable<NavGraph.Statistics> {
            StatisticsScreen()
        }

        composable<NavGraph.GameModeScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<NavGraph.GameModeScreen>()
            GameModeScreen(
                navController = navController,
                gameMode = args.gameMode
            )
        }

        composable<NavGraph.GameScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<NavGraph.GameScreen>()
            GameScreen(navController, args.difficultyLevel)
        }

        composable<NavGraph.ResultScreen>(
            typeMap = mapOf(
                typeOf<ParsedPath>() to SerializableNavType.create(serializer<ParsedPath>())
            )
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<NavGraph.ResultScreen>()
            ResultScreen(
                navController = navController,
                rate = args.rate,
                previewPaths = args.previewDrawing,
                userDrawnPaths = args.userDrawn
            )
        }
    }
}
