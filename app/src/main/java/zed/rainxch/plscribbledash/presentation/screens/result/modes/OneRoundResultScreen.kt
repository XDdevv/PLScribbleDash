package zed.rainxch.plscribbledash.presentation.screens.result.modes

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.domain.model.toPaintPath
import zed.rainxch.plscribbledash.domain.model.toPath
import zed.rainxch.plscribbledash.presentation.components.BlueButton
import zed.rainxch.plscribbledash.presentation.components.BodyMediumText
import zed.rainxch.plscribbledash.presentation.components.DisplayLargeText
import zed.rainxch.plscribbledash.presentation.components.HeadlineLargeText
import zed.rainxch.plscribbledash.presentation.components.LabelSmallText
import zed.rainxch.plscribbledash.presentation.core.model.GameModeOptions
import zed.rainxch.plscribbledash.presentation.core.navigation.NavGraph
import zed.rainxch.plscribbledash.presentation.screens.result.utils.ResultState
import zed.rainxch.plscribbledash.presentation.screens.result.vm.ResultViewModel

@Composable
fun OneRoundWonderGameScreen(
    navController: NavController,
    viewModel: ResultViewModel,
    state: ResultState.OneRoundWonder,
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
                navController.navigate(NavGraph.Home) {
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
                contentDescription = "Back",
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
                modifier = Modifier.rotate(-16f).weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LabelSmallText(
                    text = "Example",
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
                    Canvas(
                        modifier = modifier
                            .size(150.dp)
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
                        val previewPaths = state.previewPaths
                        val scaleX = canvasWidth / previewPaths.viewportWidth.toFloat()
                        val scaleY = canvasHeight / previewPaths.viewportHeight.toFloat()

                        val scale = minOf(scaleX, scaleY)
                        val scaledWidth = previewPaths.viewportWidth * scale
                        val scaledHeight = previewPaths.viewportHeight * scale
                        val translateX = (canvasWidth - scaledWidth) / 2f
                        val translateY = (canvasHeight - scaledHeight) / 2f

                        withTransform({
                            translate(left = translateX, top = translateY)
                            scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
                        }) {
                            val composePaths = previewPaths.paths.map { it.toPath() }

                            composePaths.forEachIndexed { index, path ->
                                drawPath(
                                    path = path,
                                    color = Color.Black,
                                    style = Stroke(width = previewPaths.paths[index].strokeWidth)
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .rotate(16f)
                    .offset(y = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LabelSmallText(
                    text = "Drawing",
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

                    Canvas(
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
                    ) {
                        val viewportWidth = 1000f
                        val viewportHeight = 1000f

                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        val scaleX = canvasWidth / viewportWidth
                        val scaleY = canvasHeight / viewportHeight
                        val scale = minOf(scaleX, scaleY)

                        val scaledWidth = viewportWidth * scale
                        val scaledHeight = viewportHeight * scale
                        val translateX = (canvasWidth - scaledWidth) / 2f
                        val translateY = (canvasHeight - scaledHeight) / 2f

                        withTransform({
                            translate(left = translateX, top = translateY)
                            scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
                        }) {
                            val paths = state.userDrawnPaths.map { it.toPaintPath() }
                            paths.forEach { paintPath ->
                                if (paintPath.points.size > 1) {
                                    val path = Path()
                                    path.moveTo(
                                        paintPath.points.first().x,
                                        paintPath.points.first().y
                                    )

                                    for (i in 1 until paintPath.points.size) {
                                        val to = paintPath.points[i]

                                        if (i < paintPath.points.size - 1) {
                                            val nextPoint = paintPath.points[i + 1]
                                            val controlX = to.x
                                            val controlY = to.y
                                            val endX = (to.x + nextPoint.x) / 2
                                            val endY = (to.y + nextPoint.y) / 2
                                            path.quadraticTo(
                                                controlX,
                                                controlY,
                                                endX,
                                                endY
                                            )
                                        } else {
                                            path.lineTo(to.x, to.y)
                                        }
                                    }

                                    drawPath(
                                        path = path,
                                        color = paintPath.color,
                                        style = Stroke(
                                            width = paintPath.strokeWidth,
                                            cap = StrokeCap.Round,
                                            join = StrokeJoin.Round
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        val randomTitle by rememberSaveable { mutableStateOf(viewModel.getRandomTitle(state.rate)) }
        val randomDescId by rememberSaveable {
            mutableIntStateOf(
                viewModel.getRandomFeedbackResource(
                    state.rate
                )
            )
        }

        HeadlineLargeText(
            text = randomTitle,
            color = MaterialTheme.colorScheme.primary
        )
        BodyMediumText(
            text = ContextCompat.getString(
                LocalContext.current,
                randomDescId
            ),
            textColor = MaterialTheme.colorScheme.secondary,
            align = TextAlign.Center
        )
        Spacer(Modifier.weight(1f))
        BlueButton(
            text = "TRY AGAIN",
            {
                navController.navigate(NavGraph.DifficultyModeScreen(GameModeOptions.OneRoundWonder)) {
                    popUpTo(NavGraph.Home) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.padding(24.dp)
        )
    }
}