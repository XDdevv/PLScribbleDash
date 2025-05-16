package zed.rainxch.plscribbledash.core.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonParser
import zed.rainxch.plscribbledash.core.domain.mappers.toDomain
import zed.rainxch.plscribbledash.core.domain.mappers.toSerializable
import zed.rainxch.plscribbledash.core.domain.mappers.toSerializableColor
import zed.rainxch.plscribbledash.core.domain.model.SerializableShopPen
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

class ShopPenTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromShopPen(pen: ShopPen): String {
        return gson.toJson(pen.toSerializable())
    }

    @TypeConverter
    fun toShopPen(json: String): ShopPen {
        val jsonObject = JsonParser.parseString(json).asJsonObject
        val type = jsonObject.get("type").asString
        return when (type) {
            "Basic" -> gson.fromJson(json, SerializableShopPen.Basic::class.java).toDomain()
            "Premium" -> gson.fromJson(json, SerializableShopPen.Premium::class.java).toDomain()
            "Legendary" -> gson.fromJson(json, SerializableShopPen.Legendary::class.java).toDomain()
            else -> throw IllegalArgumentException("Unknown ShopPen type $type")
        }
    }
}