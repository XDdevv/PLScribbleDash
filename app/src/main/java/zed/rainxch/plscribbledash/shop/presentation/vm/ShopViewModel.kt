package zed.rainxch.plscribbledash.shop.presentation.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.core.domain.model.CoinOperators
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen
import zed.rainxch.plscribbledash.core.domain.repository.PlayerRepository
import zed.rainxch.plscribbledash.shop.domain.ShopRepository
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val playerRepository: PlayerRepository
) : ViewModel() {
    fun getCanvasList() = shopRepository.getCanvasList()

    fun getPenList() = shopRepository.getPenList()

    val coins = playerRepository.getUserCoins()

    var showCantAffordBuySnack by mutableStateOf(false)
        private set

    fun handlePenClick(pen: ShopPen) {
        viewModelScope.launch(Dispatchers.IO) {
            if (pen.price > coins.first() && !pen.bought) {
                showCantAffordBuySnack = true
                delay(2000)
                showCantAffordBuySnack = false
                return@launch
            }

            if (!pen.bought) {
                playerRepository.buyPen(pen)
            } else {
                playerRepository.setEquippedPen(pen)
            }
        }
    }

    fun handleCanvasClick(canvas: ShopCanvas) {
        viewModelScope.launch(Dispatchers.IO) {
            if (canvas.price > coins.first()&& !canvas.bought) {
                showCantAffordBuySnack = true
                delay(2000)
                showCantAffordBuySnack = false
                return@launch
            }

            if (!canvas.bought) {
                playerRepository.buyCanvas(canvas)
            } else {
                playerRepository.setEquippedCanvas(canvas)
            }
        }
    }
}