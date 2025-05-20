package zed.rainxch.plscribbledash.core.data.utils.mappers

import zed.rainxch.plscribbledash.core.data.db.entity.ShopCanvasEntity
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas

fun ShopCanvasEntity.toShopCanvas(): ShopCanvas {
    return when (val canvas = this.canvas) {
        is ShopCanvas.Basic -> {
            ShopCanvas.Basic(
                canvas.color,
                canvas.canvasPrice,
                bought, equipped,
                type = canvas.type,
                canvasName = canvas.canvasName
            )
        }

        is ShopCanvas.Legendary -> {
            ShopCanvas.Legendary(
                canvas.imageRes,
                canvas.canvasPrice,
                bought, equipped,
                type = canvas.type,
                canvasName = canvas.canvasName
            )
        }

        is ShopCanvas.Premium -> {
            ShopCanvas.Premium(
                canvas.color,
                canvas.canvasPrice,
                bought, equipped,
                type = canvas.type,
                canvasName = canvas.canvasName
            )
        }
    }
}

fun ShopCanvas.toShopEntity(): ShopCanvasEntity {
    return when (this) {
        is ShopCanvas.Basic -> {
            ShopCanvasEntity(
                canvas = this,
                equipped = this.canvasEquipped,
                bought = this.canvasBought,
                canvasName = this.canvasName
            )
        }

        is ShopCanvas.Legendary -> {
            ShopCanvasEntity(
                canvas = this,
                equipped = this.canvasEquipped,
                bought = this.canvasBought,
                canvasName = this.canvasName
            )
        }

        is ShopCanvas.Premium -> {
            ShopCanvasEntity(
                canvas = this,
                equipped = this.canvasEquipped,
                bought = this.canvasBought,
                canvasName = this.canvasName
            )
        }
    }
}