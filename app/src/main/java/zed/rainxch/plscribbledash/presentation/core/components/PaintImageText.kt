package zed.rainxch.plscribbledash.presentation.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.presentation.components.HeadlineXSmallText
import zed.rainxch.plscribbledash.presentation.core.ui.theme.PLScribbleDashTheme

@Composable
fun CounterComponent(
    content: String,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .width(60.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeadlineXSmallText(
                text = content,
                textColor = MaterialTheme.colorScheme.onBackground
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_master),
            contentDescription = "Paint",
            modifier = Modifier
                .scale(1.1f)
                .offset(x = (-12).dp)
        )
    }
}

@Preview
@Composable
private fun PaintImageTextPreview() {
    PLScribbleDashTheme {
        CounterComponent("5")
    }
}