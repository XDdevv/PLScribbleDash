package zed.rainxch.plscribbledash.presentation.screens.game

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.presentation.components.DisplayMediumText
import zed.rainxch.plscribbledash.presentation.components.GreenButton
import zed.rainxch.plscribbledash.presentation.components.IconButtonMedium
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.presentation.screens.game.components.DrawingCanvas
import zed.rainxch.plscribbledash.presentation.screens.game.vm.GameViewModel

@Composable
fun GameScreen(
    navController: NavController,
    difficultyLevelOptions: DifficultyLevelOptions,
    modifier: Modifier = Modifier,
    viewModel : GameViewModel = hiltViewModel()
) {
    val paths by viewModel.paths.collectAsState()
    val redoPaths by viewModel.redoPaths.collectAsState()
    val currentPath by viewModel.currentPath
    val currentColor by viewModel.currentColor
    val strokeWidth by viewModel.strokeWidth

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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
            text = "Time to draw!",
            textColor = MaterialTheme.colorScheme.primary
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
            )

            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(22.dp))
            ) {
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
                val cornerRadius = 22.dp.toPx()

                drawRoundRect(
                    color = Color(0xFFF6F1EC),
                    size = Size(size.width, size.height),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius, cornerRadius),
                    style = Stroke(width = borderWidth)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
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
            GreenButton(
                text = "CLEAR CANVAS",
                onClick = {
                    viewModel.onClear()
                },
                enabled = paths.isNotEmpty()
            )
        }
    }
}