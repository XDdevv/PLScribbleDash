package zed.rainxch.plscribbledash.core.domain.model

sealed class SerializableShopPen {
    abstract val type: String
    abstract val penName: String
    data class Basic(
        val color: SerializableColor,
        val penPrice: Int,
        val penBought: Boolean,
        val penEquipped: Boolean,
        override val type: String = "Basic",
        override val penName: String
    ) : SerializableShopPen()

    data class Premium(
        val color: SerializableColor,
        val penPrice: Int,
        val penBought: Boolean,
        val penEquipped: Boolean,
        override val type: String = "Premium",
        override val penName: String
    ) : SerializableShopPen()

    data class Legendary(
        val colors: List<SerializableColor>,
        val penPrice: Int,
        val penBought: Boolean,
        val penEquipped: Boolean,
        override val type: String = "Legendary",
        override val penName: String
    ) : SerializableShopPen()
}