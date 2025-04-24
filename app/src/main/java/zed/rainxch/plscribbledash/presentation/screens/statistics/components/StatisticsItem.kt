package zed.rainxch.plscribbledash.presentation.screens.statistics.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.presentation.components.BodySmallText
import zed.rainxch.plscribbledash.presentation.components.HeadlineLargeText
import zed.rainxch.plscribbledash.presentation.core.model.Statistic
import zed.rainxch.plscribbledash.presentation.core.model.StatisticsType
import zed.rainxch.plscribbledash.presentation.core.ui.theme.PLScribbleDashTheme

@Composable
fun StatisticsItem(
    statistic: Statistic,
    modifier: Modifier = Modifier
) {
    var dominantColor by remember { mutableStateOf(Color.Gray) }
    getDominantColorFromImage(statistic.icon) {
        dominantColor = it
    }
    Row(
        modifier = modifier
            .padding(4.dp)
            .shadow(4.dp, RoundedCornerShape(24.dp), true, Color.Gray, Color.Black)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = painterResource(statistic.icon),
            contentDescription = "Icon of statistics",
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(dominantColor)
                .padding(8.dp)
        )
        BodySmallText(
            text = statistic.title,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        val progress = buildAnnotatedString {
            append(statistic.progress.toInt().toString())
            if (statistic.statisticsType == StatisticsType.PERCENTAGE) {
                append("%")
            }
        }
        HeadlineLargeText(
            text = progress.toString(),
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
fun getDominantColorFromImage(
    @DrawableRes imageRes: Int,
    onColorExtracted: (Color) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(imageRes) {
        val drawable = ContextCompat.getDrawable(context, imageRes)
        val bitmap = drawable?.toBitmap()
        bitmap?.let {
            Palette.from(it).generate { palette ->
                val dominantColor = palette?.getDominantColor(Color.Gray.toArgb())
                dominantColor?.let { onColorExtracted(Color(it).copy(alpha = .1f)) }
            }
        }
    }
}

@Preview
@Composable
fun StatisticsItemPreview(modifier: Modifier = Modifier) {
    PLScribbleDashTheme {
        StatisticsItem(
            statistic = Statistic(
                id = "for now 2",
                icon = R.drawable.ic_difficulty_master,
                title = "Nothing to track...for now",
                progress = 0f,
                statisticsType = StatisticsType.COUNT
            )
        )
    }
}