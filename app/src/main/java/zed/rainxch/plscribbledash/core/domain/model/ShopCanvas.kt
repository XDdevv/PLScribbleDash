package zed.rainxch.plscribbledash.core.domain.model

import androidx.compose.ui.graphics.Color

sealed class ShopCanvas(
    val price: Int,
    val bought: Boolean,
    val equipped: Boolean,
    val bColor: Color
) {
    abstract val type: String
    abstract val canvasName: String

    data class Basic(
        val color: Color,
        val canvasPrice: Int = 80,
        val canvasBought: Boolean = false,
        val canvasEquipped: Boolean = false,
        override val type: String = "Basic",
        override val canvasName: String
    ) : ShopCanvas(canvasPrice, canvasBought, canvasEquipped, color)

    data class Premium(
        val color: Color,
        val canvasPrice: Int = 150,
        val canvasBought: Boolean = false,
        val canvasEquipped: Boolean = false,
        override val type: String = "Premium",
        override val canvasName: String
    ) : ShopCanvas(canvasPrice, canvasBought, canvasEquipped, color)

    data class Legendary(
        val imageRes: Int,
        val canvasPrice: Int = 250,
        val canvasBought: Boolean = false,
        val canvasEquipped: Boolean = false,
        override val type: String = "Legendary",
        override val canvasName: String
    ) : ShopCanvas(canvasPrice, canvasBought, canvasEquipped, Color.Black)
}
