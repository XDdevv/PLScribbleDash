package zed.rainxch.plscribbledash.shop.data

import zed.rainxch.plscribbledash.core.domain.model.SerializableShopPen

interface ShopRepository {

    fun getCoins() : Int
    fun getPens() : List<SerializableShopPen>
}