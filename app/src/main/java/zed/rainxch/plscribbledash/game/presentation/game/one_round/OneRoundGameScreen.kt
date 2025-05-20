package zed.rainxch.plscribbledash.game.presentation.game.one_round

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.presentation.components.DisplayMediumText
import zed.rainxch.plscribbledash.core.presentation.components.GreenButton
import zed.rainxch.plscribbledash.core.presentation.components.HeadlineMediumText
import zed.rainxch.plscribbledash.core.presentation.components.IconButtonMedium
import zed.rainxch.plscribbledash.core.presentation.components.LabelSmallText
import zed.rainxch.plscribbledash.core.presentation.navigation.NavGraph
import zed.rainxch.plscribbledash.game.domain.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.game.domain.model.toStringDTO
import zed.rainxch.plscribbledash.game.presentation.components.DrawingCanvas
import zed.rainxch.plscribbledash.game.presentation.components.PreviewPathCanvas
import zed.rainxch.plscribbledash.game.presentation.game.one_round.utils.OneRoundGameState
import zed.rainxch.plscribbledash.game.presentation.game.one_round.vm.OneRoundGameViewModel
import zed.rainxch.plscribbledash.game.presentation.result.utils.ResultState

@Composable
fun OneRoundGameScreen(
    navController: NavController,
    difficultyLevelOption: DifficultyLevelOptions,
    modifier: Modifier = Modifier,
) {
    val viewModel: OneRoundGameViewModel = hiltViewModel()
    val paths by viewModel.paths.collectAsState()
    val redoPaths by viewModel.redoPaths.collectAsState()
    val currentPath by viewModel.currentPath
    val gameState by viewModel.gameState.collectAsState()
    val timer by viewModel.previewTimer.collectAsState()

    BackHandler {
        viewModel.onClear()
        navController.popBackStack()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                viewModel.onClear()
                navController.popBackStack()
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

        Spacer(modifier = Modifier.height(120.dp))

        when (val state = gameState) {
            OneRoundGameState.PREVIEW -> {
                val parsedPath = viewModel.randomPath.collectAsState().value
                DisplayMediumText(
                    text = stringResource(R.string.ready_set),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = modifier
                        .shadow(4.dp, RoundedCornerShape(32.dp))
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    PreviewPathCanvas(
                        parsedPath = parsedPath,
                        modifier = Modifier.size(350.dp)
                    )
                }
                Spacer(Modifier.height(6.dp))
                LabelSmallText(
                    text = stringResource(R.string.example),
                    textColor = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.weight(1f))
                HeadlineMediumText(
                    text = stringResource(R.string.seconds_left, timer),
                    textColor = MaterialTheme.colorScheme.primary
                )
            }

            OneRoundGameState.PLAY -> {
                DisplayMediumText(
                    text = stringResource(R.string.time_to_draw),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = modifier
                        .shadow(4.dp, RoundedCornerShape(32.dp))
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    DrawingCanvas(
                        paths = paths,
                        currentPath = currentPath,
                        onTouchStart = { viewModel.onTouchStart(it) },
                        onTouchMove = { viewModel.onTouchMove(it) },
                        onTouchEnd = { viewModel.onTouchEnd() },
                        modifier = Modifier
                            .size(350.dp),
                        shopCanvas = viewModel.canvasBackground,
                        shopPen = viewModel.penColor
                    )
                }
                Spacer(Modifier.height(6.dp))
                LabelSmallText(
                    text = stringResource(R.string.your_drawing),
                    textColor = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        IconButtonMedium(
                            iconId = R.drawable.ic_prev_step,
                            onClick = {
                                viewModel.onUndo()
                            },
                            modifier = Modifier.padding(8.dp),
                            enabled = paths.isNotEmpty()
                        )

                        IconButtonMedium(
                            iconId = R.drawable.ic_next_step,
                            onClick = {
                                viewModel.onRedo()
                            },
                            modifier = Modifier.padding(8.dp),
                            enabled = redoPaths.isNotEmpty()
                        )
                    }
                    GreenButton(
                        text = stringResource(R.string.done),
                        onClick = {
                            viewModel.onFinish(difficultyLevelOption)
                        },
                        enabled = paths.isNotEmpty()
                    )
                }
            }

            is OneRoundGameState.FINISHED -> {
                val paths by remember { mutableStateOf(paths.map { it.toStringDTO() }) }
                navController.navigate(
                    NavGraph.ResultScreen(
                        ResultState.OneRoundWonder(
                            score = state.score,
                            coins = state.coins,
                            previewPaths = state.path,
                            userDrawnPaths = paths,
                        )
                    )
                )
                viewModel.onClear()
            }
        }


    }
}