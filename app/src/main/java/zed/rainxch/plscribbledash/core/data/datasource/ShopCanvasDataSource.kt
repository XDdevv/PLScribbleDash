package zed.rainxch.plscribbledash.core.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import zed.rainxch.plscribbledash.core.data.datasource.static_.shopCanvasList
import zed.rainxch.plscribbledash.core.data.db.dao.ShopCanvasDao
import zed.rainxch.plscribbledash.core.data.utils.mappers.toShopCanvas
import zed.rainxch.plscribbledash.core.data.utils.mappers.toShopEntity
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

    suspend fun getIdByCanvas(canvas: ShopCanvas) : Int {
        return shopDao.getCanvasIdByCanvas(canvas) ?: -1
    }

    suspend fun setEquippedCanvas(canvas: ShopCanvas) {
        val canvasId = shopDao.getCanvasIdByCanvas(canvas) ?: 0
//        shopDao.updateCanvas()
        shopDao.getCanvasCount()
    }

    suspend fun insertCanvases() {
        shopDao.insertAllCanvases(shopCanvasList.map { it.toShopEntity() })
    }

    fun getCanvasById(id: Int) = shopDao.getCanvasById(id)

    fun canvasCount() = shopDao.getCanvasCount()
}