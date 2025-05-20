package zed.rainxch.plscribbledash.core.data.utils.mappers

import zed.rainxch.plscribbledash.core.data.db.entity.ShopPenEntity
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

fun ShopPenEntity.toShopPen(): ShopPen {
    return when (val pen = this.pen) {
        is ShopPen.Basic -> {
            ShopPen.Basic(pen.color, pen.penPrice, bought, equipped, pen.type, penName)
        }

        is ShopPen.Legendary -> {
            ShopPen.Legendary(pen.colors, pen.penPrice, bought, equipped, pen.type, penName)
        }

        is ShopPen.Premium -> {
            ShopPen.Premium(pen.color, pen.penPrice, bought, equipped, pen.type, penName)
        }
    }
}

fun ShopPen.toShopEntity(): ShopPenEntity {
    return when (this) {
        is ShopPen.Basic -> {
            ShopPenEntity(pen = this, equipped = this.penEquipped, bought = this.penBought, penName = this.penName)
        }

        is ShopPen.Premium -> {
            ShopPenEntity(pen = this, equipped = this.penEquipped, bought = this.penBought, penName = this.penName)
        }

        is ShopPen.Legendary -> {
            ShopPenEntity(pen = this, equipped = this.penEquipped, bought = this.penBought, penName = this.penName)
        }

    }
}