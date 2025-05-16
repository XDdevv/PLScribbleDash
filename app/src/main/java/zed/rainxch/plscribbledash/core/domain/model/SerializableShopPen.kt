package zed.rainxch.plscribbledash.core.domain.model

sealed class SerializableShopPen {
    abstract val type: String
    data class Basic(
        val color: SerializableColor,
        val penPrice: Int,
        val penBought: Boolean,
        val penEquipped: Boolean,
        override val type: String = "Basic",
    ) : SerializableShopPen()

    data class Premium(
        val color: SerializableColor,
        val penPrice: Int,
        val penBought: Boolean,
        val penEquipped: Boolean,
        override val type: String = "Premium",
    ) : SerializableShopPen()

    data class Legendary(
        val colors: List<SerializableColor>,
        val penPrice: Int,
        val penBought: Boolean,
        val penEquipped: Boolean,
        override val type: String = "Legendary",
    ) : SerializableShopPen()
}