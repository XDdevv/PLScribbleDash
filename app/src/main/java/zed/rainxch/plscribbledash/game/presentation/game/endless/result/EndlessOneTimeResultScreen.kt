package zed.rainxch.plscribbledash.game.presentation.game.endless.result

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.hilt.navigation.compose.hiltViewModel
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.BlueButton
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.BodyMediumText
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.DisplayLargeText
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.GreenButton
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.HeadlineLargeText
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.LabelSmallText
import zed.rainxch.plscribbledash.game.presentation.components.PreviewPathCanvas
import zed.rainxch.plscribbledash.game.presentation.components.ScaledDrawingCanvas
import zed.rainxch.plscribbledash.game.presentation.game.endless.utils.EndlessGameState
import zed.rainxch.plscribbledash.game.presentation.game.endless.vm.EndlessGameViewModel

@Composable
fun EndlessOneTimeResultScreen(
    modifier: Modifier = Modifier,
    state: EndlessGameState.RESULT,
    onFinishClicked: () -> Unit,
    onNextDrawingClicked: () -> Unit,
) {
    val viewModel: EndlessGameViewModel = hiltViewModel()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(82.dp))

            DisplayLargeText(
                text = "${state.score}%",
                textColor = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 32.dp,
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
                            modifier = Modifier
                                .size(150.dp)
                                .shadow(4.dp, RoundedCornerShape(32.dp))
                                .background(Color.White)
                                .padding(12.dp)
                        ) {
                            PreviewPathCanvas(
                                parsedPath = state.previewPaths,
                                modifier = Modifier.size(150.dp)
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
                            modifier = Modifier
                                .size(150.dp)
                                .shadow(4.dp, RoundedCornerShape(32.dp))
                                .background(Color.White)
                                .padding(12.dp)
                        ) {
                            ScaledDrawingCanvas(
                                userPaths = state.userDrawnPath,
                                modifier = Modifier.size(150.dp),
                                drawBackground = true,
                                drawGrid = true,
                                canvasColor = viewModel.canvasBackground,
                                shopPen = viewModel.penColor
                            )
                        }
                    }

                }

                Image(
                    painter = painterResource(viewModel.getCheckboxImageId(state.score)),
                    contentDescription = stringResource(R.string.checkbox_image),
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = (16).dp)
                        .shadow(4.dp, CircleShape)
                )
            }

            Spacer(Modifier.height(32.dp))

            val title by remember { mutableStateOf(viewModel.getRandomTitle(state.score)) }
            val description by remember { mutableStateOf(viewModel.getRandomFeedback(state.score)) }

            HeadlineLargeText(
                text = title.asString(),
                color = MaterialTheme.colorScheme.primary
            )

            BodyMediumText(
                text = description.asString(),
                textColor = MaterialTheme.colorScheme.secondary,
                align = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

//            RowIconTextComponent(
//                R.drawable.ic_coin,
//                state.gainedCoins.toString()
//            )
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BlueButton(
                text = stringResource(R.string.finish),
                onClick = onFinishClicked,
                modifier = Modifier
                    .width(336.dp)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
            )
            if (viewModel.isSuccess(state.score)) {
                GreenButton(
                    text = stringResource(R.string.next_drawing),
                    onClick = onNextDrawingClicked,
                    modifier = Modifier
                        .width(336.dp)
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    buttonModifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}