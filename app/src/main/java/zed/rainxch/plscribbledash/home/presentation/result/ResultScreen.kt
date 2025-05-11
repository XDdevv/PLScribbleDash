package zed.rainxch.plscribbledash.home.presentation.result

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import zed.rainxch.plscribbledash.home.presentation.result.modes.EndlessResultScreen
import zed.rainxch.plscribbledash.home.presentation.result.modes.OneRoundWonderGameScreen
import zed.rainxch.plscribbledash.home.presentation.result.modes.SpeedDrawResultScreen
import zed.rainxch.plscribbledash.home.presentation.result.utils.ResultState
import zed.rainxch.plscribbledash.home.presentation.result.vm.ResultViewModel

@Composable
fun ResultScreen(
    navController: NavController,
    resultState: ResultState,
) {
    val viewModel: ResultViewModel = hiltViewModel()
    BackHandler { }

    when (val state = resultState) {
        is ResultState.OneRoundWonder -> {
            OneRoundWonderGameScreen(
                state = state,
                viewModel = viewModel,
                navController = navController
            )
        }

        is ResultState.SpeedDraw -> {
            SpeedDrawResultScreen(
                state = state,
                viewModel = viewModel,
                navController = navController
            )
        }

        is ResultState.Endless -> {
            EndlessResultScreen(
                state = state,
                viewModel = viewModel,
                navController = navController
            )
        }
    }

}
