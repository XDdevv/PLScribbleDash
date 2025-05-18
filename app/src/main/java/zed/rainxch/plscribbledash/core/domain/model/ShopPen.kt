package zed.rainxch.plscribbledash.core.domain.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

sealed class ShopPen() {
    abstract val type: String
    data class Basic(
        val color: Color,
        val penPrice: Int = 25,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Basic",
    ) : ShopPen()

    data class Premium(
        val color: Color,
        val penPrice: Int = 120,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Premium",
    ) : ShopPen()

    data class Legendary(
        val colors: List<Color>,
        val penPrice: Int = 999,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Legendary",
    ) : ShopPen() {
        fun toBrush() = Brush.horizontalGradient(colors)
    }
}