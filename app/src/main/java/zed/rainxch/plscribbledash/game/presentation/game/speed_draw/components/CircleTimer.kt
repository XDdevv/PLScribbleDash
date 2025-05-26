package zed.rainxch.plscribbledash.game.presentation.game.speed_draw.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.HeadlineXSmallText
import zed.rainxch.plscribbledash.game.presentation.game.speed_draw.utils.TimeUI

const val WARNING_TIME_SECONDS = 30

@Composable
fun CircleTimer(
    timeUI: TimeUI,
    modifier: Modifier = Modifier
) {
    val shadowColor = Color(0x20726558)
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(CircleShape)
            .shadow(elevation = 6.dp, shape = CircleShape, ambientColor = shadowColor)
            .background(Color.White)
    ) {
        val textColor =
            if (timeUI.timeInt <= WARNING_TIME_SECONDS) Color.Red else MaterialTheme.colorScheme.onBackground
        HeadlineXSmallText(
            text = timeUI.timeMinAndSec,
            textColor = textColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}