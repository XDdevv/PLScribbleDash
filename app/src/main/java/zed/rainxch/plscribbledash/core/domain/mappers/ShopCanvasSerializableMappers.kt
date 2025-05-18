package zed.rainxch.plscribbledash.core.domain.mappers

import zed.rainxch.plscribbledash.core.domain.model.SerializableShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas

fun ShopCanvas.toSerializable(): SerializableShopCanvas {
    return when (this) {
        is ShopCanvas.Basic -> {
            SerializableShopCanvas.Basic(
                this.color.toSerializableColor(),
                this.canvasPrice,
                this.canvasBought,
                this.canvasEquipped
            )
        }
        is ShopCanvas.Legendary -> {
            SerializableShopCanvas.Legendary(
                this.imageRes,
                this.canvasPrice,
                this.canvasBought,
                this.canvasEquipped
            )
        }
        is ShopCanvas.Premium -> {
            SerializableShopCanvas.Premium(
                this.color.toSerializableColor(),
                this.canvasPrice,
                this.canvasBought,
                this.canvasEquipped
            )
        }
    }
}

fun SerializableShopCanvas.toDomain(): ShopCanvas {
    return when (this) {
        is SerializableShopCanvas.Basic -> {
            ShopCanvas.Basic(serializableColor.toColor(), canvasPrice = price, canvasBought = bought, canvasEquipped = equipped, type = type)
        }

        is SerializableShopCanvas.Legendary -> {
            ShopCanvas.Legendary(imageRes, canvasPrice = price, canvasBought = bought, canvasEquipped = equipped, type = type)
        }

        is SerializableShopCanvas.Premium -> {
            ShopCanvas.Premium(serializableColor.toColor(), canvasPrice = price, canvasBought = bought, canvasEquipped = equipped, type = type)
        }
    }
}