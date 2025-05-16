package zed.rainxch.plscribbledash.shop.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.shop.presentation.vm.ShopViewModel
import androidx.compose.runtime.getValue

@Composable
fun ShopItemsPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = hiltViewModel()
) {
    val tabTitles = listOf(stringResource(R.string.pen), stringResource(R.string.canvas))
    val coroutineScope = rememberCoroutineScope()
    val shopCanvases by viewModel.getCanvases().collectAsState(listOf(ShopCanvas.Basic(Color.Green)))

    Column(modifier = modifier) {
        ShopTabItems(
            tabs = tabTitles,
            selectedIndex = state.currentPage,
            onTabSelected = { index ->
                coroutineScope.launch {
                    state.animateScrollToPage(index)
                }
            }
        )

        HorizontalPager(
            state = state,
        ) {
            if (state.currentPage == 0) {
                Text("pen boi's")
                println("hallo $shopCanvases")
            }
            if (state.currentPage == 1) {
                Text("canvas boi's")
            }
        }
    }
}