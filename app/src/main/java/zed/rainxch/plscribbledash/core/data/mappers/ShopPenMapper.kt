package zed.rainxch.plscribbledash.core.data.mappers

import zed.rainxch.plscribbledash.core.data.db.entity.ShopPenEntity
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

fun ShopPenEntity.toShopPen(): ShopPen {
    return when (val pen = this.pen) {
        is ShopPen.Basic -> {
            ShopPen.Basic(pen.color)
        }

        is ShopPen.Legendary -> {
            ShopPen.Legendary(pen.colors)
        }

        is ShopPen.Premium -> {
            ShopPen.Premium(pen.color)
        }
    }
}

fun ShopPen.toShopEntity(): ShopPenEntity {
    return when (this) {
        is ShopPen.Basic -> {
            ShopPenEntity(pen = this)
        }

        is ShopPen.Legendary -> {
            ShopPenEntity(pen = this)
        }

        is ShopPen.Premium -> {
            ShopPenEntity(pen = this)
        }
    }
}