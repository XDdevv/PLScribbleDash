package zed.rainxch.plscribbledash.main.domain

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.core.data.datasource.static_.shopCanvasList
import zed.rainxch.plscribbledash.core.data.datasource.static_.shopPenList
import zed.rainxch.plscribbledash.core.data.db.dao.ShopCanvasDao
import zed.rainxch.plscribbledash.core.data.db.dao.ShopPenDao
import zed.rainxch.plscribbledash.core.data.utils.mappers.toShopEntity
import javax.inject.Inject

class InitializeShopUseCase @Inject constructor(
    private val canvasDao: ShopCanvasDao,
    private val penDao: ShopPenDao,
) {

    suspend operator fun invoke() = coroutineScope {
        val canvasCount = canvasDao.getCanvasCount()
        val penCount = penDao.getPenCount()

        if (canvasCount <= 0 && penCount <= 0) {
            launch { canvasDao.insertAllCanvases(shopCanvasList.map { it.toShopEntity() }) }
            launch { penDao.insertAllPens(shopPenList.map { it.toShopEntity() }) }
        }
    }
}
