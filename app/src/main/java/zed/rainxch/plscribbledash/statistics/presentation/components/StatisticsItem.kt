package zed.rainxch.plscribbledash.statistics.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.presentation.components.BodySmallText
import zed.rainxch.plscribbledash.core.presentation.components.HeadlineLargeText
import zed.rainxch.plscribbledash.core.presentation.ui.theme.PLScribbleDashTheme
import zed.rainxch.plscribbledash.statistics.presentation.models.Statistic
import zed.rainxch.plscribbledash.statistics.presentation.models.StatisticsType

@Composable
fun StatisticsItem(
    statistic: Statistic,
    modifier: Modifier = Modifier
) {
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
                .background(statistic.dominantColor)
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
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Preview
@Composable
fun StatisticsItemPreview() {
    PLScribbleDashTheme {
        StatisticsItem(
            statistic = Statistic(
                id = "for now 2",
                icon = R.drawable.ic_difficulty_master,
                title = "Nothing to track...for now",
                progress = 0f,
                statisticsType = StatisticsType.COUNT
            ),
        )
    }
}