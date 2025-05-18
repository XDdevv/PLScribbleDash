package zed.rainxch.plscribbledash.main.domain

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.core.data.datasource.ShopCanvasDataSource
import zed.rainxch.plscribbledash.core.data.datasource.ShopPenDataSource
import javax.inject.Inject

class InitializeShopUseCase @Inject constructor(
    private val canvasDataSource: ShopCanvasDataSource,
    private val penDataSource: ShopPenDataSource,
) {

    suspend operator fun invoke() = coroutineScope {
        val canvasCount = canvasDataSource.canvasCount()
        val penCount = penDataSource.penCount()

        if (canvasCount <= 0 && penCount <= 0) {
            launch { canvasDataSource.insertCanvases() }
            launch { penDataSource.insertPens() }
        }
    }
}
