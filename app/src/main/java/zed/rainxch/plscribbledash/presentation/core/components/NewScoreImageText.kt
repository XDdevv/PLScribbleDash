package zed.rainxch.plscribbledash.presentation.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.presentation.components.HeadlineXSmallText

@Composable
fun NewScoreImageText(
    content: String,
    modifier: Modifier = Modifier
) {
    Box (modifier = modifier) {
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .background(Brush.horizontalGradient(listOf(Color(0xffFFDA35), Color(0xffFF9600))))
                .border(4.dp, Color.White, CircleShape)
                .padding(start = 22.dp, end = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeadlineXSmallText(
                text = content,
                textColor = Color.White
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_high_score_pad),
            contentDescription = "Star",
            modifier = Modifier
                .scale(1.1f)
                .offset(x = (-12).dp)
        )
    }
}