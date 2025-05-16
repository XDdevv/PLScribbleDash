package zed.rainxch.plscribbledash.shop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.presentation.components.LabelXLargeText
import zed.rainxch.plscribbledash.core.presentation.ui.theme.PLScribbleDashTheme
import zed.rainxch.plscribbledash.game.presentation.components.RowIconTextComponent
import zed.rainxch.plscribbledash.shop.presentation.components.ShopItemsPager
import zed.rainxch.plscribbledash.shop.presentation.vm.ShopViewModel

@Composable
fun ShopScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val viewModel : ShopViewModel = hiltViewModel()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelXLargeText(
                text = stringResource(R.string.shop),
                color = MaterialTheme.colorScheme.onBackground
            )
            RowIconTextComponent(
                icon = R.drawable.ic_coin,
                content = "300" // COINS
            )

        }

        Spacer(Modifier.height(8.dp))

        ShopItemsPager(
            state = pagerState,
            viewModel = viewModel,
            modifier = Modifier.padding(16.dp)
        )

    }
}

@Preview
@Composable
private fun ShopScreenPreview() {
    PLScribbleDashTheme {
        ShopScreen()
    }
}