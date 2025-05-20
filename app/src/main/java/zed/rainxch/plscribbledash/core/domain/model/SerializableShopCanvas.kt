package zed.rainxch.plscribbledash.core.domain.model

sealed class SerializableShopCanvas {
    abstract val type: String
    abstract val canvasName: String

    data class Basic(
        val serializableColor: SerializableColor,
        val price: Int,
        val bought: Boolean,
        val equipped: Boolean,
        override val type: String = "Basic",
        override val canvasName: String
    ) : SerializableShopCanvas()

    data class Premium(
        val serializableColor: SerializableColor,
        val price: Int,
        val bought: Boolean,
        val equipped: Boolean,
        override val type: String = "Premium",
        override val canvasName: String
    ) : SerializableShopCanvas()

    data class Legendary(
        val imageRes: Int,
        val price: Int,
        val bought: Boolean,
        val equipped: Boolean,
        override val type: String = "Legendary",
        override val canvasName: String
    ) : SerializableShopCanvas()
}
