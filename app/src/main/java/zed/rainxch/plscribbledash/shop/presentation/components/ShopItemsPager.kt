package zed.rainxch.plscribbledash.shop.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.shop.presentation.vm.ShopViewModel

const val SHOP_PENS_INDEX = 0
const val SHOP_CANVAS_INDEX = 1

@Composable
fun ShopItemsPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = hiltViewModel(),
    snackState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()

    val tabTitles = listOf(stringResource(R.string.pen), stringResource(R.string.canvas))
    val shopCanvasList by viewModel.getCanvasList().collectAsState(emptyList())
    val shopPenList by viewModel.getPenList().collectAsState(emptyList())

    val coins by viewModel.coins.collectAsState(0)

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
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
        ) {
            if (state.currentPage == SHOP_PENS_INDEX) {
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(shopPenList) { pen ->
                        val cantAffordBuy = !pen.bought && pen.price > coins
                        ShopItemPen(
                            shopPen = pen,
                            onItemClicked = {
                                viewModel.handlePenClick(pen)
                            },
                            cantAfford = cantAffordBuy
                        )
                    }
                }
            }
            if (state.currentPage == SHOP_CANVAS_INDEX) {
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(shopCanvasList) { canvas ->
                        val canAffordBuy = !canvas.bought && canvas.price > coins
                        ShopItemCanvas(
                            shopCanvas = canvas,
                            onItemClicked = {
                                viewModel.handleCanvasClick(canvas)
                            },
                            canAfford = canAffordBuy
                        )
                    }
                }
            }
        }

        LaunchedEffect(viewModel.showCantAffordBuySnack) {
            if (viewModel.showCantAffordBuySnack) {
                coroutineScope.launch {
                    snackState.showSnackbar("You don't have enough money, go work bro )")
                }
            }
        }
    }
}