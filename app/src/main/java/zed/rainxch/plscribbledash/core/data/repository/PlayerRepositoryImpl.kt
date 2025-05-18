package zed.rainxch.plscribbledash.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import zed.rainxch.plscribbledash.core.data.datasource.ShopCanvasDataSource
import zed.rainxch.plscribbledash.core.data.datasource.ShopPenDataSource
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
    private val shopCanvasDataSource: ShopCanvasDataSource,
    private val shopPenDataSource: ShopPenDataSource,
) : PlayerRepository {
    override fun getUserCoins(): Flow<Int> {
        return dataStoreManager.coins
    }

    override suspend fun updateCoins(coin: Int, action : CoinOperators) {
        val currentCoins = dataStoreManager.coins.first()
        when (action) {
            CoinOperators.PLUS -> dataStoreManager.setCoins(currentCoins + coin)
            CoinOperators.SUBTRACT -> dataStoreManager.setCoins(currentCoins - coin)
        }
    }

    override suspend fun getEquippedPen(): ShopPen {
        val penId = dataStoreManager.equippedCanvasId.first()
        val pen = shopPenDataSource.getPenById(penId).first()
        return pen.toShopPen()
    }

    override suspend fun getEquippedCanvas(): ShopCanvas {
        val canvasId = dataStoreManager.equippedCanvasId.first()
        val canvas = shopCanvasDataSource.getCanvasById(canvasId).first()
        return canvas.toShopCanvas()
    }

    override suspend fun setEquippedCanvas(shopCanvas: ShopCanvas) {
        val id = shopCanvasDataSource.getIdByCanvas(shopCanvas)
        dataStoreManager.setEquippedCanvasId(id)
    }

    override suspend fun setEquippedPen(shopPen: ShopPen) {
        val id = shopPenDataSource.getIdByPen(shopPen)
        dataStoreManager.setEquippedPenId(id)
    }
}