package zed.rainxch.plscribbledash.presentation.screens.result

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
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.domain.model.ParsedPath
import zed.rainxch.plscribbledash.domain.model.toPaintPath
import zed.rainxch.plscribbledash.domain.model.toPath
import zed.rainxch.plscribbledash.presentation.components.BlueButton
import zed.rainxch.plscribbledash.presentation.components.BodyMediumText
import zed.rainxch.plscribbledash.presentation.components.DisplayLargeText
import zed.rainxch.plscribbledash.presentation.components.HeadlineLargeText
import zed.rainxch.plscribbledash.presentation.components.LabelSmallText
import zed.rainxch.plscribbledash.presentation.core.model.GameModeOptions
import zed.rainxch.plscribbledash.presentation.core.navigation.NavGraph
import zed.rainxch.plscribbledash.presentation.screens.result.modes.EndlessResultScreen
import zed.rainxch.plscribbledash.presentation.screens.result.modes.OneRoundWonderGameScreen
import zed.rainxch.plscribbledash.presentation.screens.result.modes.SpeedDrawResultScreen
import zed.rainxch.plscribbledash.presentation.screens.result.utils.ResultState
import zed.rainxch.plscribbledash.presentation.screens.result.vm.ResultViewModel

@Composable
fun ResultScreen(
    navController: NavController,
    resultState: ResultState,
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = hiltViewModel()
) {
    BackHandler { }

    when (val state = resultState) {
        is ResultState.OneRoundWonder -> {
            OneRoundWonderGameScreen(
                state = state,
                viewModel = viewModel,
                navController = navController
            )
        }

        is ResultState.SpeedDraw -> {
            SpeedDrawResultScreen(
                state = state,
                viewModel = viewModel,
                navController = navController
            )
        }

        is ResultState.Endless -> {
            EndlessResultScreen(
                state = state,
                viewModel = viewModel,
                navController = navController
            )
        }
    }

}
