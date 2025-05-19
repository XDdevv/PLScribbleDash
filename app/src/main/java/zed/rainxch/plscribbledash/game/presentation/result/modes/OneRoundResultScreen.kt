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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.presentation.components.BlueButton
import zed.rainxch.plscribbledash.core.presentation.components.BodyMediumText
import zed.rainxch.plscribbledash.core.presentation.components.DisplayLargeText
import zed.rainxch.plscribbledash.core.presentation.components.HeadlineLargeText
import zed.rainxch.plscribbledash.core.presentation.components.LabelSmallText
import zed.rainxch.plscribbledash.core.presentation.navigation.NavGraph
import zed.rainxch.plscribbledash.game.domain.model.GameModeOptions
import zed.rainxch.plscribbledash.game.domain.model.toPaintPath
import zed.rainxch.plscribbledash.game.presentation.components.PreviewPathCanvas
import zed.rainxch.plscribbledash.game.presentation.components.ScaledDrawingCanvas
import zed.rainxch.plscribbledash.game.presentation.result.utils.ResultState
import zed.rainxch.plscribbledash.game.presentation.result.vm.ResultViewModel

@Composable
fun OneRoundWonderGameScreen(
    navController: NavController,
    viewModel: ResultViewModel,
    state: ResultState.OneRoundWonder,
    modifier: Modifier = Modifier
) {
    val userDrawnPaths = state.userDrawnPaths.map { it.toPaintPath() }
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

        Spacer(Modifier.height(84.dp))

        DisplayLargeText(
            text = "${state.rate}%",
            textColor = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                32.dp,
                Alignment.CenterHorizontally
            )
        ) {
            Column(
                modifier = Modifier
                    .rotate(-16f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LabelSmallText(
                    text = stringResource(R.string.example),
                    textColor = MaterialTheme.colorScheme.secondary
                )
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = modifier
                        .size(150.dp)
                        .shadow(4.dp, RoundedCornerShape(32.dp))
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    PreviewPathCanvas(
                        parsedPath = state.previewPaths
                    )
                }
            }

            Column(
                modifier = Modifier
                    .rotate(16f)
                    .offset(y = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LabelSmallText(
                    text = stringResource(R.string.drawing),
                    textColor = MaterialTheme.colorScheme.secondary
                )
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = modifier
                        .size(150.dp)
                        .shadow(4.dp, RoundedCornerShape(32.dp))
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    ScaledDrawingCanvas(
                        userPaths = userDrawnPaths,
                        shopPen = viewModel.penColor,
                        canvasColor = viewModel.canvasBackground
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        val title by remember { mutableStateOf(viewModel.getRandomTitle(state.rate)) }
        val description by remember { mutableStateOf(viewModel.getRandomFeedback(state.rate)) }

        HeadlineLargeText(
            text = title.asString(),
            color = MaterialTheme.colorScheme.primary
        )
        BodyMediumText(
            text = description.asString(),
            textColor = MaterialTheme.colorScheme.secondary,
            align = TextAlign.Center
        )
        Spacer(Modifier.weight(1f))
        BlueButton(
            text = stringResource(R.string.try_again),
            {
                navController.navigate(NavGraph.DifficultyModeScreen(GameModeOptions.OneRoundWonder)) {
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