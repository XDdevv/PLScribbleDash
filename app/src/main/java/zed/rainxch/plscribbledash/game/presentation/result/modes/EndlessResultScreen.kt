package zed.rainxch.plscribbledash.game.presentation.result.modes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.BlueButton
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.BodyMediumText
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.DisplayLargeText
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.HeadlineLargeText
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.LabelSmallText
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.LabelXLargeText
import zed.rainxch.plscribbledash.core.presentation.navigation.NavGraph
import zed.rainxch.plscribbledash.game.domain.model.GameModeOptions
import zed.rainxch.plscribbledash.game.presentation.components.RowIconTextComponent
import zed.rainxch.plscribbledash.game.presentation.components.NewScoreImageText
import zed.rainxch.plscribbledash.game.presentation.result.utils.ResultState
import zed.rainxch.plscribbledash.game.presentation.result.vm.ResultViewModel

@Composable
fun EndlessResultScreen(
    navController: NavController,
    viewModel: ResultViewModel,
    state: ResultState.Endless,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                navController.navigate(NavGraph.HomeScreen) {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            modifier = modifier
                .align(Alignment.End),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        LabelXLargeText(
            text = stringResource(R.string.time_s_up),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                4.dp
            )
        ) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DisplayLargeText(
                        text = "${state.averageScore}%",
                        textColor = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                if (state.isAverageAccuracyHighScore) {
                    NewScoreImageText(
                        content = stringResource(R.string.new_high_score),
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }

            val title by remember { mutableStateOf(viewModel.getRandomTitle(state.averageScore)) }
            val description by remember { mutableStateOf(viewModel.getRandomFeedback(state.averageScore)) }

            Spacer(Modifier.height(12.dp))
            HeadlineLargeText(
                text = title.asString(),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(4.dp))
            BodyMediumText(
                text = description.asString(),
                textColor = MaterialTheme.colorScheme.secondary,
                align = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(16.dp))
            Row (
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    RowIconTextComponent(
                        icon = R.drawable.ic_palette,
                        content = state.mehPlusCount.toString(),
                        backgroundColor = if (state.isMehPlusHighScore) Color(0xffED6363) else MaterialTheme.colorScheme.surfaceContainerHigh,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    )

                    if (state.isMehPlusHighScore) {
                        LabelSmallText(
                            text = stringResource(R.string.new_high),
                            textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Spacer(Modifier.width(16.dp))
                RowIconTextComponent(
                    R.drawable.ic_coin,
                    "+${state.coins}"
                )
            }
        }


        Spacer(Modifier.weight(1f))
        BlueButton(
            text = stringResource(R.string.draw_again),
            {
                navController.navigate(NavGraph.DifficultyModeScreen(GameModeOptions.EndlessMode)) {
                    popUpTo(NavGraph.HomeScreen) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.padding(24.dp)
        )
    }
}