package zed.rainxch.plscribbledash.core.domain.model

import androidx.compose.ui.graphics.Color

sealed class ShopCanvas(
    val price: Int,
    val bought: Boolean,
    val equipped: Boolean,
) {
    abstract val type: String

    data class Basic(
        val color: Color,
        val canvasPrice: Int = 80,
        val canvasBought: Boolean = false,
        val canvasEquipped: Boolean = false,
        override val type: String = "Basic"
    ) : ShopCanvas(canvasPrice, canvasBought, canvasEquipped)

    data class Premium(
        val color: Color,
        val canvasPrice: Int = 150,
        val canvasBought: Boolean = false,
        val canvasEquipped: Boolean = false,
        override val type: String = "Premium"
    ) : ShopCanvas(canvasPrice, canvasBought, canvasEquipped)

    data class Legendary(
        val imageRes: Int,
        val canvasPrice: Int = 250,
        val canvasBought: Boolean = false,
        val canvasEquipped: Boolean = false,
        override val type: String = "Legendary"
    ) : ShopCanvas(canvasPrice, canvasBought, canvasEquipped)
}
