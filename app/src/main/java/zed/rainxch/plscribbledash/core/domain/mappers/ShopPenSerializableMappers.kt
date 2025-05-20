package zed.rainxch.plscribbledash.core.domain.mappers

import androidx.compose.ui.graphics.Color
import zed.rainxch.plscribbledash.core.domain.model.SerializableColor
import zed.rainxch.plscribbledash.core.domain.model.SerializableShopPen
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

fun Color.toSerializableColor(): SerializableColor = SerializableColor(red, green, blue, alpha)
fun List<Color>.toSerializableColors(): List<SerializableColor> =
    this.map { it.toSerializableColor() }

fun SerializableColor.toColor(): Color = Color(red, green, blue, alpha)

fun ShopPen.toSerializable(): SerializableShopPen {
    return when (this) {
        is ShopPen.Basic -> {
            SerializableShopPen.Basic(
                this.color.toSerializableColor(),
                this.penPrice,
                this.penBought,
                this.penEquipped,
                penName = this.penName
            )
        }

        is ShopPen.Premium -> {
            SerializableShopPen.Premium(
                this.color.toSerializableColor(),
                this.penPrice,
                this.penBought,
                this.penEquipped,
                penName = this.penName
            )
        }

        is ShopPen.Legendary -> {
            SerializableShopPen.Legendary(
                this.colors.toSerializableColors(),
                this.penPrice,
                this.penBought,
                this.penEquipped,
                penName = this.penName
            )
        }
    }
}

fun SerializableShopPen.toDomain(): ShopPen {
    return when (this) {
        is SerializableShopPen.Basic -> {
            ShopPen.Basic(color.toColor(), penBought = penBought, penEquipped = penEquipped, type = type, penName = penName)
        }

        is SerializableShopPen.Legendary -> {
            ShopPen.Legendary(colors.map { it.toColor() }, penBought = penBought, penEquipped = penEquipped, type = type, penName = penName)
        }

        is SerializableShopPen.Premium -> {
            ShopPen.Premium(color.toColor(), penBought = penBought, penEquipped = penEquipped, type = type, penName = penName)
        }
    }
}