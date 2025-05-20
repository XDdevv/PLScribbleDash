package zed.rainxch.plscribbledash.core.domain.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

sealed class ShopPen(
    val price: Int,
    val bought: Boolean,
    val equipped: Boolean,
    val penColor: Color
) {
    abstract val type: String
    abstract val penName: String

    data class Basic(
        val color: Color,
        val penPrice: Int = 25,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Basic",
        override val penName: String
    ) : ShopPen(penPrice, penBought, penEquipped, color)

    data class Premium(
        val color: Color,
        val penPrice: Int = 120,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Premium",
        override val penName: String
    ) : ShopPen(penPrice, penBought, penEquipped, color)

    data class Legendary(
        val colors: List<Color>,
        val penPrice: Int = 999,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Legendary",
        override val penName: String
    ) : ShopPen(penPrice, penBought, penEquipped, Color.Black) {
        fun brush() = Brush.linearGradient(colors)
    }
}