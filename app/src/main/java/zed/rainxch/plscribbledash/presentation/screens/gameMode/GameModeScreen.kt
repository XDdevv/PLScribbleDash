package zed.rainxch.plscribbledash.presentation.screens.gameMode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.presentation.components.BodyMediumText
import zed.rainxch.plscribbledash.presentation.components.DisplayMediumText
import zed.rainxch.plscribbledash.presentation.core.model.GameModeOptions
import zed.rainxch.plscribbledash.presentation.core.navigation.NavGraph
import zed.rainxch.plscribbledash.presentation.screens.gameMode.components.DifficultyLevelItem
import zed.rainxch.plscribbledash.presentation.screens.gameMode.vm.GameModeViewModel

@Composable
fun GameModeScreen(
    navController: NavController,
    gameMode: GameModeOptions,
    modifier: Modifier = Modifier,
    viewModel: GameModeViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = modifier
                        .align(Alignment.End),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(120.dp))

                DisplayMediumText(
                    text = "Start drawing!",
                    textColor = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                BodyMediumText(
                    text = "Choose a difficulty setting",
                    textColor = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(48.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(viewModel.getDifficultyLevels()) { gameMode ->
                    DifficultyLevelItem(
                        difficultyLevelItem = gameMode,
                        onItemClicked = {
                            navController.navigate(NavGraph.GameScreen(difficultyLevel = it.difficultyLevel))
                        }
                    )
                }
            }
        }

    }
}