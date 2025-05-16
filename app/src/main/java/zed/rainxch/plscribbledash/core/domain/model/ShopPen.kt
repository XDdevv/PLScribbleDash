package zed.rainxch.plscribbledash.core.domain.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

sealed class ShopPen(
    val price: Int,
    val bought: Boolean,
    val equipped: Boolean
) {
    abstract val type: String
    data class Basic(
        val color: Color,
        val penPrice: Int = 25,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Basic",
    ) : ShopPen(penPrice, penBought, penEquipped)

    data class Premium(
        val color: Color,
        val penPrice: Int = 120,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Premium",
    ) : ShopPen(penPrice, penBought, penEquipped)

    data class Legendary(
        val colors: List<Color>,
        val penPrice: Int = 999,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Legendary",
    ) : ShopPen(penPrice, penBought, penEquipped) {
        fun toBrush() = Brush.horizontalGradient(colors)
    }
}