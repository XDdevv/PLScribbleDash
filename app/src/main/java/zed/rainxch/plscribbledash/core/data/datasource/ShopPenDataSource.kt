package zed.rainxch.plscribbledash.core.data.datasource

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.core.data.datasource.static_.shopPenList
import zed.rainxch.plscribbledash.core.data.db.dao.ShopPenDao
import zed.rainxch.plscribbledash.core.data.mappers.toShopEntity
import zed.rainxch.plscribbledash.core.data.mappers.toShopPen
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


    suspend fun insertPens() {
        shopDao.insertAllPens(shopPenList.map { it.toShopEntity() })
    }

    fun penCount() = shopDao.getPenCount()

    suspend fun clearPenList() = shopDao.clearPenList()

    fun buyPen() {

    }
}