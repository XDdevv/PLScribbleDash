package zed.rainxch.plscribbledash.core.domain.mappers

import zed.rainxch.plscribbledash.core.domain.model.SerializableShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas

fun ShopCanvas.toSerializable(): SerializableShopCanvas {
    return when (this) {
        is ShopCanvas.Basic -> {
            SerializableShopCanvas.Basic(
                this.color.toSerializableColor(),
                this.penPrice,
                this.penBought,
                this.penEquipped
            )
        }
        is ShopCanvas.Legendary -> {
            SerializableShopCanvas.Legendary(
                this.imageRes,
                this.penPrice,
                this.penBought,
                this.penEquipped
            )
        }
        is ShopCanvas.Premium -> {
            SerializableShopCanvas.Premium(
                this.color.toSerializableColor(),
                this.penPrice,
                this.penBought,
                this.penEquipped
            )
        }
    }
}

fun SerializableShopCanvas.toDomain(): ShopCanvas {
    return when (this) {
        is SerializableShopCanvas.Basic -> {
            ShopCanvas.Basic(serializableColor.toColor())
        }

        is SerializableShopCanvas.Legendary -> {
            ShopCanvas.Legendary(imageRes)
        }

        is SerializableShopCanvas.Premium -> {
            ShopCanvas.Premium(serializableColor.toColor())
        }
    }
}