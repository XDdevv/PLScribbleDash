package zed.rainxch.plscribbledash.shop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import zed.rainxch.plscribbledash.core.data.db.dao.ShopCanvasDao
import zed.rainxch.plscribbledash.core.data.db.dao.ShopPenDao
import zed.rainxch.plscribbledash.core.data.utils.mappers.toShopCanvas
import zed.rainxch.plscribbledash.core.data.utils.mappers.toShopPen
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen
import zed.rainxch.plscribbledash.shop.domain.ShopRepository
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val penDao: ShopPenDao,
    private val canvasDao: ShopCanvasDao
) : ShopRepository {
    override fun getCanvasList(): Flow<List<ShopCanvas>> {
        return canvasDao.getCanvasList()
            .map { list -> list.map { entity -> entity.toShopCanvas() } }
            .flowOn(Dispatchers.Default)
    }

    override fun getPenList(): Flow<List<ShopPen>> {
        return penDao.getPenList()
            .map { list -> list.map { entity -> entity.toShopPen() } }
            .flowOn(Dispatchers.Default)
    }
}