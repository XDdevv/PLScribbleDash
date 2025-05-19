package zed.rainxch.plscribbledash.core.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import zed.rainxch.plscribbledash.core.data.datasource.static_.shopPenList
import zed.rainxch.plscribbledash.core.data.db.dao.ShopPenDao
import zed.rainxch.plscribbledash.core.data.db.entity.ShopPenEntity
import zed.rainxch.plscribbledash.core.data.utils.mappers.toShopEntity
import zed.rainxch.plscribbledash.core.data.utils.mappers.toShopPen
import zed.rainxch.plscribbledash.core.domain.model.ShopPen
import javax.inject.Inject

class ShopPenDataSource @Inject constructor(
    private val shopDao: ShopPenDao
) {

    fun getPenList(): Flow<List<ShopPen>> {
        return shopDao.getPenList()
            .map { it.map { it.toShopPen() } }
            .flowOn(Dispatchers.Default)
    }

    suspend fun getIdByPen(pen: ShopPen): Int {
        return shopDao.getPenIdByType(pen) ?: -1
    }

    suspend fun insertPens() {
        shopDao.insertAllPens(shopPenList.map { it.toShopEntity() })
    }

    suspend fun getEquippedPen() : ShopPenEntity {
        return shopDao.getEquippedPen()
    }

    suspend fun getPenEntityByPen(pen: ShopPen) = shopDao.getPenEntityByPen(pen)

    suspend fun updatePen(pen: ShopPenEntity) {
        shopDao.updatePen(pen)
    }

    fun penCount() = shopDao.getPenCount()

    suspend fun getPenById(id: Int) = shopDao.getPenById(id)
}