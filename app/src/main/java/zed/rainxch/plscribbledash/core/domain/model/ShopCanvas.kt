package zed.rainxch.plscribbledash.core.domain.model

import androidx.compose.ui.graphics.Color

sealed class ShopCanvas() {
    abstract val type: String

    data class Basic(
        val color: Color,
        val penPrice: Int = 80,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Basic"
    ) : ShopCanvas()

    data class Premium(
        val color: Color,
        val penPrice: Int = 150,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Premium"
    ) : ShopCanvas()

    data class Legendary(
        val imageRes: Int,
        val penPrice: Int = 250,
        val penBought: Boolean = false,
        val penEquipped: Boolean = false,
        override val type: String = "Legendary"
    ) : ShopCanvas()
}
