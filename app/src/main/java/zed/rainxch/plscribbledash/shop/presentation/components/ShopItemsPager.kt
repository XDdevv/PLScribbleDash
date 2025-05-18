package zed.rainxch.plscribbledash.shop.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
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

const val SHOP_PENS_INDEX = 0
const val SHOP_CANVAS_INDEX = 1

@Composable
fun ShopItemsPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = hiltViewModel()
) {
    val tabTitles = listOf(stringResource(R.string.pen), stringResource(R.string.canvas))
    val coroutineScope = rememberCoroutineScope()
    val shopCanvasList by viewModel.getCanvasList().collectAsState(listOf(ShopCanvas.Basic(Color.Green)))
    val shopPenList by viewModel.getPenList().collectAsState(listOf(ShopCanvas.Basic(Color.Green)))

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
            if (state.currentPage == SHOP_PENS_INDEX) {
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(shopPenList) { pen ->
//                        ShopItemPen(shopPen = pen)
                    }
                }
            }
            if (state.currentPage == 1) {
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(shopCanvasList) { canvas ->
                        ShopItemCanvas(shopCanvas = canvas)
                    }
                }
            }
        }
    }
}