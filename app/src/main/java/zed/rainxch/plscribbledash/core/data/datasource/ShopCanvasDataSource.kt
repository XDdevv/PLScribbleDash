package zed.rainxch.plscribbledash.core.data.datasource

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.core.data.datasource.static_.shopCanvasList
import zed.rainxch.plscribbledash.core.data.db.dao.ShopCanvasDao
import zed.rainxch.plscribbledash.core.data.mappers.toShopCanvas
import zed.rainxch.plscribbledash.core.data.mappers.toShopEntity
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import javax.inject.Inject

class ShopCanvasDataSource @Inject constructor(
    private val shopDao: ShopCanvasDao
) {
    fun getCanvasList(): Flow<List<ShopCanvas>> {
        return shopDao.getCanvasList()
            .map { it.map { it.toShopCanvas() } }
            .flowOn(Dispatchers.Default)
    }

    suspend fun insertCanvases() {
        shopDao.insertAllCanvases(shopCanvasList.map { it.toShopEntity() })
    }

    fun canvasCount() = shopDao.getCanvasCount()

    suspend fun clearCanvasList() = shopDao.clearCanvasList()

    fun buyCanvas() {

    }
}