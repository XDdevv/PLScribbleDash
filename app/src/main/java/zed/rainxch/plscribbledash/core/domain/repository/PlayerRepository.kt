package zed.rainxch.plscribbledash.core.domain.repository

import kotlinx.coroutines.flow.Flow
import zed.rainxch.plscribbledash.core.domain.model.CoinOperators
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

interface PlayerRepository {

    fun getUserCoins(): Flow<Int>
    suspend fun updateCoins(coin: Int, action : CoinOperators)

    suspend fun getEquippedPen(): ShopPen
    suspend fun getEquippedCanvas(): ShopCanvas

    suspend fun setEquippedCanvas(shopCanvas: ShopCanvas)
    suspend fun setEquippedPen(shopPen: ShopPen)
}