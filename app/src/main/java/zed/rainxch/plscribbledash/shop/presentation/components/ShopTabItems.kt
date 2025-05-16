package zed.rainxch.plscribbledash.shop.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ShopTabItems(
    tabs: List<String>,
    onTabSelected: (Int) -> Unit,
    selectedIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        tabs.forEachIndexed { index, value ->
            val isSelected = selectedIndex == index
            val containerColor =
                if (isSelected) MaterialTheme.colorScheme.surfaceContainerHigh
                else Color(0xfff6f0eb)
            val contentColor =
                if (isSelected) MaterialTheme.colorScheme.onBackground
                else MaterialTheme.colorScheme.onSurface
            Surface(
                color = containerColor,
                contentColor = contentColor,
                onClick = { onTabSelected(index) },
                modifier = Modifier.weight(1f)
                    .padding(horizontal = 8.dp)
                    .offset(y = if(!isSelected) (-2).dp else 0.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Text(
                    text = value,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}