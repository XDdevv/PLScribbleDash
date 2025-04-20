package zed.rainxch.plscribbledash.presentation.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import zed.rainxch.plscribbledash.domain.model.ParsedPath

object PathSerializer {
    
    private val gson = Gson()
    
    fun parsedPathToStringWithGson(parsedPath: ParsedPath): String {
        return gson.toJson(parsedPath)
    }
    
    fun stringToParsedPathWithGson(jsonString: String): ParsedPath {
        val type = object : TypeToken<ParsedPath>() {}.type
        return gson.fromJson(jsonString, type)
    }
}