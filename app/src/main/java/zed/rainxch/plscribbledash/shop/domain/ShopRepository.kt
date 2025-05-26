package zed.rainxch.plscribbledash.shop.domain

import kotlinx.coroutines.flow.Flow
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

interface ShopRepository {
    fun getCanvasList() : Flow<List<ShopCanvas>>
    fun getPenList(): Flow<List<ShopPen>>
}