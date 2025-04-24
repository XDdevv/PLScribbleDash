package zed.rainxch.plscribbledash.presentation.screens.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.domain.model.toPath
import zed.rainxch.plscribbledash.domain.model.toStringDTO
import zed.rainxch.plscribbledash.presentation.components.DisplayMediumText
import zed.rainxch.plscribbledash.presentation.components.GreenButton
import zed.rainxch.plscribbledash.presentation.components.HeadlineMediumText
import zed.rainxch.plscribbledash.presentation.components.IconButtonMedium
import zed.rainxch.plscribbledash.presentation.components.LabelSmallText
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.presentation.core.model.GameState
import zed.rainxch.plscribbledash.presentation.core.navigation.NavGraph
import zed.rainxch.plscribbledash.presentation.core.utils.PathSerializer
import zed.rainxch.plscribbledash.presentation.screens.game.components.DrawingCanvas
import zed.rainxch.plscribbledash.presentation.screens.game.vm.GameViewModel

@Composable
fun GameScreen(
    navController: NavController,
    difficultyLevelOption: DifficultyLevelOptions,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel()
) {
    val paths by viewModel.paths.collectAsState()
    val redoPaths by viewModel.redoPaths.collectAsState()
    val currentPath by viewModel.currentPath
    val gameState by viewModel.gameState.collectAsState()
    val timer by viewModel.timer.collectAsState()

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
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(120.dp))

        when (val state = gameState) {
            GameState.PREVIEW -> {
                val parsedPath = viewModel.randomPath.collectAsState().value
                DisplayMediumText(
                    text = "Ready, set...",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .shadow(4.dp, RoundedCornerShape(32.dp))
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    Canvas(
                        modifier = modifier
                            .size(350.dp)
                            .background(Color(0xFFFFFFFF))
                            .clip(RoundedCornerShape(18.dp))
                            .clipToBounds()
                            .drawBehind {
                                val cellSize = size.width / 3
                                for (i in 1..2) {
                                    drawLine(
                                        color = Color(0xFFF6F1EC),
                                        start = Offset(i * cellSize, 0f),
                                        end = Offset(i * cellSize, size.height),
                                        strokeWidth = 1.dp.toPx(),
                                        alpha = .7f
                                    )
                                    drawLine(
                                        color = Color(0xFFF6F1EC),
                                        start = Offset(0f, i * cellSize),
                                        end = Offset(size.width, i * cellSize),
                                        strokeWidth = 1.dp.toPx(),
                                        alpha = .7f
                                    )
                                }

                                val borderWidth = 2.dp.toPx()
                                val cornerRadius = 18.dp.toPx()

                                drawRoundRect(
                                    color = Color(0xFFF6F1EC),
                                    size = Size(size.width, size.height),
                                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                                    style = Stroke(width = borderWidth)
                                )
                            }
                    ) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height

                        val scaleX = canvasWidth / (parsedPath?.viewportWidth?.toFloat() ?: 0f)
                        val scaleY = canvasHeight / (parsedPath?.viewportHeight?.toFloat() ?: 0f)

                        val scale = minOf(scaleX, scaleY)
                        val scaledWidth = (parsedPath?.viewportWidth ?: 0f) * scale
                        val scaledHeight = (parsedPath?.viewportHeight ?: 0f) * scale
                        val translateX = (canvasWidth - scaledWidth) / 2f
                        val translateY = (canvasHeight - scaledHeight) / 2f

                        withTransform({
                            translate(left = translateX, top = translateY)
                            scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
                        }) {
                            val composePaths = parsedPath?.paths?.map { it.toPath() }

                            composePaths?.forEachIndexed { index, path ->
                                drawPath(
                                    path = path,
                                    color = Color.Black,
                                    style = Stroke(width = parsedPath.paths[index].strokeWidth)
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(6.dp))
                LabelSmallText(
                    text = "Example",
                    textColor = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.weight(1f))
                HeadlineMediumText(
                    text = "$timer seconds left",
                    textColor = MaterialTheme.colorScheme.primary
                )
            }

            GameState.PLAY -> {
                DisplayMediumText(
                    text = "Time to draw!",
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
                            .size(350.dp)
                            .background(Color(0xFFFFFFFF))
                            .clip(RoundedCornerShape(18.dp))
                            .clipToBounds()
                            .drawBehind {
                                val cellSize = size.width / 3
                                for (i in 1..2) {
                                    drawLine(
                                        color = Color(0xFFF6F1EC),
                                        start = Offset(i * cellSize, 0f),
                                        end = Offset(i * cellSize, size.height),
                                        strokeWidth = 1.dp.toPx(),
                                        alpha = .7f
                                    )
                                    drawLine(
                                        color = Color(0xFFF6F1EC),
                                        start = Offset(0f, i * cellSize),
                                        end = Offset(size.width, i * cellSize),
                                        strokeWidth = 1.dp.toPx(),
                                        alpha = .7f
                                    )
                                }

                                val borderWidth = 2.dp.toPx()
                                val cornerRadius = 18.dp.toPx()

                                drawRoundRect(
                                    color = Color(0xFFF6F1EC),
                                    size = Size(size.width, size.height),
                                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                                    style = Stroke(width = borderWidth)
                                )
                            }
                    )
                }
                Spacer(Modifier.height(6.dp))
                LabelSmallText(
                    text = "Your Drawing",
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
                        text = "DONE",
                        onClick = {
                            viewModel.onFinish(difficultyLevelOption)
                        },
                        enabled = paths.isNotEmpty()
                    )
                }
            }

            is GameState.FINISHED -> {
                val paths by remember { mutableStateOf(paths.map { it.toStringDTO() }) }
                navController.navigate(
                    NavGraph.ResultScreen(
                        state.score,
                        state.path,
                        paths
                    )
                )
                viewModel.onClear()
            }
        }


    }
}