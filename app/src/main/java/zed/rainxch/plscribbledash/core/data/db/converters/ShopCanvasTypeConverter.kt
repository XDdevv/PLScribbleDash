package zed.rainxch.plscribbledash.core.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonParser
import zed.rainxch.plscribbledash.core.domain.mappers.toDomain
import zed.rainxch.plscribbledash.core.domain.mappers.toSerializable
import zed.rainxch.plscribbledash.core.domain.model.SerializableShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas

class ShopCanvasTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromShopCanvas(canvas: ShopCanvas) : String {
        return gson.toJson(canvas.toSerializable())
    }

    @TypeConverter
    fun toShopCanvas(json: String): ShopCanvas {
        val jsonObject = JsonParser.parseString(json).asJsonObject
        val type = jsonObject.get("type").asString
        return when(type) {
            "Basic" -> gson.fromJson(json, SerializableShopCanvas.Basic::class.java).toDomain()
            "Premium" -> gson.fromJson(json, SerializableShopCanvas.Premium::class.java).toDomain()
            "Legendary" -> gson.fromJson(json, SerializableShopCanvas.Legendary::class.java).toDomain()
            else -> throw IllegalArgumentException("Unknown ShopCanvas type $type")
        }
    }
}