package zed.rainxch.plscribbledash.core.domain.model

sealed class SerializableShopCanvas {
    abstract val type: String

    data class Basic(
        val serializableColor: SerializableColor,
        val price: Int,
        val bought: Boolean,
        val equipped: Boolean,
        override val type: String = "Basic"
    ) : SerializableShopCanvas()

    data class Premium(
        val serializableColor: SerializableColor,
        val price: Int,
        val bought: Boolean,
        val equipped: Boolean,
        override val type: String = "Premium"
    ) : SerializableShopCanvas()

    data class Legendary(
        val imageRes: Int,
        val price: Int,
        val bought: Boolean,
        val equipped: Boolean,
        override val type: String = "Legendary"
    ) : SerializableShopCanvas()
}
