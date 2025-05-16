package zed.rainxch.plscribbledash.core.data.datasource

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.core.data.db.dao.ShopCanvasDao
import zed.rainxch.plscribbledash.core.data.mappers.toShopCanvas
import zed.rainxch.plscribbledash.core.data.mappers.toShopEntity
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import javax.inject.Inject

class ShopCanvasDataSource @Inject constructor(
    private val shopDao: ShopCanvasDao
) {
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    fun getCanvasList(): Flow<List<ShopCanvas>> {
        return shopDao.getCanvasList()
            .map { it.map { it.toShopCanvas() } }
            .flowOn(Dispatchers.Default)
    }

    fun insertCanvases() {
        coroutineScope.launch {
            shopDao.insertAllCanvases(
                listOf(
                    ShopCanvas.Basic(Color.Green).toShopEntity(),
                    ShopCanvas.Premium(Color.Green).toShopEntity(),
                    ShopCanvas.Basic(Color.Green).toShopEntity(),
                    ShopCanvas.Basic(Color.Green).toShopEntity()
                )
            )
        }
    }

    fun buyCanvas() {

    }
}