package zed.rainxch.plscribbledash.home.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.core.presentation.components.HeadlineMediumText
import zed.rainxch.plscribbledash.home.domain.model.GameModeItem

@Composable
fun GameModeItem(
    gameModeItem: GameModeItem,
    borderColor: Color,
    onItemClick : (GameModeItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(borderColor)
            .padding(8.dp)
            .clickable {
                onItemClick(gameModeItem)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeadlineMediumText(
                text = gameModeItem.title,
                textColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
                    .padding(18.dp)
            )
            Image(
                painter = painterResource(id = gameModeItem.image),
                contentDescription = "Image of game mode",
                modifier = Modifier.weight(1f),
                alignment = Alignment.BottomEnd
            )
        }
    }
}
