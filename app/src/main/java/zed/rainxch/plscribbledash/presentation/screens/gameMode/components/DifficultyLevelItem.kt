package zed.rainxch.plscribbledash.presentation.screens.gameMode.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.presentation.components.LabelMediumText
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelItem
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions

@Composable
fun DifficultyLevelItem(
    difficultyLevelItem: DifficultyLevelItem,
    onItemClicked: (DifficultyLevelItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(100.dp)
            .background(Color.Transparent)
            .offset(y = if (difficultyLevelItem.difficultyLevel == DifficultyLevelOptions.Challenging) (-16).dp else 0.dp)
            .clickable {
                onItemClicked(difficultyLevelItem)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(difficultyLevelItem.icon),
            contentDescription = difficultyLevelItem.title,
            modifier = Modifier
                .size(90.dp)
                .shadow(6.dp, CircleShape)
                .background(Color.White)
                .clip(CircleShape)
                .padding(
                    if (difficultyLevelItem.difficultyLevel == DifficultyLevelOptions.Master) 8.dp else 0.dp
                )
                .padding(
                    bottom = if (difficultyLevelItem.difficultyLevel == DifficultyLevelOptions.Beginner) 20.dp else 0.dp,
                    start = if (difficultyLevelItem.difficultyLevel == DifficultyLevelOptions.Beginner) 20.dp else 0.dp
                )
        )
        Spacer(modifier = Modifier.height(14.dp))
        LabelMediumText(
            text = difficultyLevelItem.title,
            textColor = MaterialTheme.colorScheme.onBackground
        )
    }
}