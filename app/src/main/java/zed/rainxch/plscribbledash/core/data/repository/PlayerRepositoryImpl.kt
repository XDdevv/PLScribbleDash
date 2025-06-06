package zed.rainxch.plscribbledash.core.data.repository

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.core.data.db.dao.ShopCanvasDao
import zed.rainxch.plscribbledash.core.data.db.dao.ShopPenDao
import zed.rainxch.plscribbledash.core.data.utils.managers.DataStoreManager
import zed.rainxch.plscribbledash.core.data.utils.mappers.toShopCanvas
import zed.rainxch.plscribbledash.core.data.utils.mappers.toShopPen
import zed.rainxch.plscribbledash.core.domain.model.CoinOperators
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen
import zed.rainxch.plscribbledash.core.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val shopDao: ShopCanvasDao,
    private val penDao: ShopPenDao,
) : PlayerRepository {
    override fun getUserCoins(): Flow<Int> {
        return dataStoreManager.coins
    }

    override suspend fun updateCoins(coin: Int, action: CoinOperators) {
        val currentCoins = dataStoreManager.coins.first()
        when (action) {
            CoinOperators.PLUS -> dataStoreManager.setCoins(currentCoins + coin)
            CoinOperators.SUBTRACT -> dataStoreManager.setCoins(currentCoins - coin)
        }
    }

    override suspend fun getEquippedPen(): ShopPen {
        val penId = dataStoreManager.equippedPenId.first()
        val pen = penDao.getPenById(penId).first()
        return pen.toShopPen()
    }

    override suspend fun getEquippedCanvas(): ShopCanvas {
        val canvasId = dataStoreManager.equippedCanvasId.first()
        val canvas = shopDao.getCanvasById(canvasId).first()
        return canvas.toShopCanvas()
    }

    override suspend fun buyCanvas(canvas: ShopCanvas): Unit = coroutineScope {
        val currentCoins = dataStoreManager.coins.first()
        launch { dataStoreManager.setCoins(currentCoins - canvas.price) }
        launch { setEquippedCanvas(canvas) }
    }

    override suspend fun buyPen(pen: ShopPen): Unit = coroutineScope {
        val currentCoins = dataStoreManager.coins.first()
        launch { dataStoreManager.setCoins(currentCoins - pen.price) }
        launch { setEquippedPen(pen) }
    }

    override suspend fun setEquippedCanvas(canvas: ShopCanvas) {
        val entity = shopDao.getCanvasEntityByCanvasName(canvas.canvasName)
        val equippedCanvas = shopDao.getEquippedCanvas()

        dataStoreManager.setEquippedCanvasId(entity.id)
        shopDao.updateCanvas(equippedCanvas.copy(equipped = false))
        shopDao.updateCanvas(entity.copy(equipped = true, bought = true))
    }

    override suspend fun setEquippedPen(pen: ShopPen) {
        val entity = penDao.getPenEntityByPenName(pen.penName)
        val equippedPen = penDao.getEquippedPen()

        dataStoreManager.setEquippedPenId(entity.id)
        penDao.updatePen(equippedPen.copy(equipped = false))
        penDao.updatePen(entity.copy(equipped = true, bought = true))
    }

}