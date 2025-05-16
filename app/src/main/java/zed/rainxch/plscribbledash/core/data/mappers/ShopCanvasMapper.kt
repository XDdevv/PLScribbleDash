package zed.rainxch.plscribbledash.core.data.mappers

import zed.rainxch.plscribbledash.core.data.db.entity.ShopCanvasEntity
import zed.rainxch.plscribbledash.core.domain.mappers.toColor
import zed.rainxch.plscribbledash.core.domain.model.SerializableShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas

fun ShopCanvasEntity.toShopCanvas(): ShopCanvas {
    return when (val canvas = this.canvas) {
        is ShopCanvas.Basic -> {
            ShopCanvas.Basic(canvas.color)
        }
        is ShopCanvas.Legendary -> {
            ShopCanvas.Legendary(canvas.imageRes)
        }
        is ShopCanvas.Premium -> {
            ShopCanvas.Premium(canvas.color)
        }
    }
}

fun ShopCanvas.toShopEntity(): ShopCanvasEntity {
    return when (this) {
        is ShopCanvas.Basic -> {
            ShopCanvasEntity(canvas = this)
        }
        is ShopCanvas.Legendary -> {
            ShopCanvasEntity(canvas = this)
        }
        is ShopCanvas.Premium -> {
            ShopCanvasEntity(canvas = this)
        }
    }
}