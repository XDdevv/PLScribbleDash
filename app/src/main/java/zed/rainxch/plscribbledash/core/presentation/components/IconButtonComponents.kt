package zed.rainxch.plscribbledash.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonMedium(
    @DrawableRes iconId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(64.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(
                color = if (enabled) Color(0xFFEEE7E0) else Color(0x80EEE7E0)
            )
            .clickable(enabled = enabled, onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Icon",
            tint = if (enabled) Color(0xFF514437) else Color(0x80514437),
            modifier = Modifier.size(24.dp)
        )
    }
}
