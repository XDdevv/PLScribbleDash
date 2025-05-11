package zed.rainxch.plscribbledash.home.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import zed.rainxch.plscribbledash.core.presentation.components.BodyMediumText
import zed.rainxch.plscribbledash.core.presentation.components.DisplayMediumText
import zed.rainxch.plscribbledash.core.presentation.components.HeadlineMediumText
import zed.rainxch.plscribbledash.core.presentation.navigation.NavGraph
import zed.rainxch.plscribbledash.home.data.datasource.getGameModes
import zed.rainxch.plscribbledash.home.presentation.home.components.GameModeItem

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFFEFAF6), Color(0xFFFFF1E2))))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeadlineMediumText(
                text = "ScribbleDash",
                textColor = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(120.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayMediumText(
                    text = "Start drawing!",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                BodyMediumText(
                    text = "Select game mode",
                    textColor = MaterialTheme.colorScheme.onSurface,
                    align = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        items(getGameModes()) { gameModeItem ->
            GameModeItem(
                gameModeItem = gameModeItem,
                borderColor = gameModeItem.borderColor,
                onItemClick = {
                    navController.navigate(NavGraph.DifficultyModeScreen(gameModeItem.gameMode))
                }
            )
        }
    }
}