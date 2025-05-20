package zed.rainxch.plscribbledash.game.presentation.game.endless

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
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
import zed.rainxch.plscribbledash.game.presentation.components.RowIconTextComponent
import zed.rainxch.plscribbledash.game.presentation.components.DrawingCanvas
import zed.rainxch.plscribbledash.game.presentation.components.PreviewPathCanvas
import zed.rainxch.plscribbledash.game.presentation.components.drawGridLines
import zed.rainxch.plscribbledash.game.presentation.game.endless.result.EndlessOneTimeResultScreen
import zed.rainxch.plscribbledash.game.presentation.game.endless.utils.EndlessGameState
import zed.rainxch.plscribbledash.game.presentation.game.endless.vm.EndlessGameViewModel
import zed.rainxch.plscribbledash.game.presentation.result.utils.ResultState

@Composable
fun EndlessGameScreen(
    navController: NavController,
    difficultyLevelOption: DifficultyLevelOptions,
    modifier: Modifier = Modifier,
) {
    val viewModel: EndlessGameViewModel = hiltViewModel()

    val paths by viewModel.paths.collectAsState()
    val redoPaths by viewModel.redoPaths.collectAsState()
    val currentPath by viewModel.currentPath
    val gameState by viewModel.gameState.collectAsState()
    val timer by viewModel.previewTimer.collectAsState()
    val paintCounter by viewModel.paintCounter.collectAsState()

    BackHandler {
        viewModel.onClear()
        navController.popBackStack()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            RowIconTextComponent(
                icon = R.drawable.ic_palette,
                content = paintCounter.toString(),
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = {
                    viewModel.onClear()
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }


        when (val state = gameState) {
            EndlessGameState.PREVIEW -> {
                Spacer(modifier = Modifier.height(120.dp))

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

            EndlessGameState.PLAY -> {
                Spacer(modifier = Modifier.height(120.dp))

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
                        shopPen = viewModel.penColor,
                        shopCanvas = viewModel.canvasBackground
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
                            viewModel.onDoneClick(difficultyLevelOption)
                        },
                        enabled = paths.isNotEmpty()
                    )
                }
            }

            is EndlessGameState.FINISHED -> {
                navController.navigate(
                    NavGraph.ResultScreen(
                        resultState = ResultState.Endless(
                            averageScore = state.averageScore,
                            mehPlusCount = state.mehPlusCount,
                            isMehPlusHighScore = state.isMehPlusHighScore,
                            isAverageAccuracyHighScore = state.isAverageAccuracyHighScore,
                            coins = state.coins
                        )
                    )
                )
                viewModel.onClear()
            }

            is EndlessGameState.RESULT -> {
                EndlessOneTimeResultScreen(
                    state = EndlessGameState.RESULT(
                        score = state.score,
                        previewPaths = state.previewPaths,
                        userDrawnPath = state.userDrawnPath,
                        gainedCoins = state.gainedCoins
                    ),
                    onFinishClicked = {
                        viewModel.onFinishClick()
                        viewModel.onClear()
                    },
                    onNextDrawingClicked = {
                        viewModel.onNextDrawingClick()
                        viewModel.onClear()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }


    }
}