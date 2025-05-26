package zed.rainxch.plscribbledash.game.domain.utils

import android.content.Context
import android.util.TypedValue
import org.xmlpull.v1.XmlPullParser
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath
import zed.rainxch.plscribbledash.game.domain.model.PathData
import javax.inject.Inject

class VectorDrawableParser @Inject constructor(
    private val context: Context,
) {

    fun getPathData(drawableResId: Int): ParsedPath {
        val parser = context.resources.getXml(drawableResId)

        val paths = mutableListOf<PathData>()
        var width = 0
        var height = 0
        var viewportWidth = 0f
        var viewportHeight = 0f

        try {
            while (parser.eventType != XmlPullParser.END_DOCUMENT) {
                if (parser.eventType == XmlPullParser.START_TAG) {
                    when (parser.name) {
                        "vector" -> {
                            width = parseDimensionValue(parser.getAttr("width"))
                            height = parseDimensionValue(parser.getAttr("height"))
                            viewportWidth = parser.getAttr("viewportWidth").toFloatOrDefault()
                            viewportHeight = parser.getAttr("viewportHeight").toFloatOrDefault()
                        }

                        "path" -> {
                            val pathData = parser.getAttr("pathData")
                            val strokeWidth = parser.getAttr("strokeWidth").toFloatOrDefault()
                            val fillColor = parser.getAttr("fillColor")
                            val strokeColor = parser.getAttr("strokeColor")

                            paths.add(PathData(pathData, strokeWidth, fillColor, strokeColor))
                        }
                    }
                }
                parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            parser.close()
        }

        return ParsedPath(paths, width, height, viewportWidth, viewportHeight)
    }

    private fun parseDimensionValue(value: String): Int {
        if (value.isEmpty()) return 0

        return try {
            if (value.startsWith("@")) {
                val resourceId = value.substring(1).toIntOrNull() ?: 0
                context.resources.getDimensionPixelSize(resourceId)
            } else {
                val unitMap = mapOf(
                    "dp" to TypedValue.COMPLEX_UNIT_DIP,
                    "dip" to TypedValue.COMPLEX_UNIT_DIP,
                    "sp" to TypedValue.COMPLEX_UNIT_SP,
                    "px" to TypedValue.COMPLEX_UNIT_PX,
                    "in" to TypedValue.COMPLEX_UNIT_IN,
                    "mm" to TypedValue.COMPLEX_UNIT_MM,
                    "pt" to TypedValue.COMPLEX_UNIT_PT
                )

                val numericPart = value.takeWhile { it.isDigit() || it == '.' }
                val unit = value.substring(numericPart.length)
                val numericValue = numericPart.toFloatOrNull() ?: return 0
                val unitType = unitMap[unit] ?: TypedValue.COMPLEX_UNIT_PX

                TypedValue.applyDimension(unitType, numericValue, context.resources.displayMetrics).toInt()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    // --- Extension Helpers ---

    private fun XmlPullParser.getAttr(name: String): String {
        return (0 until attributeCount)
            .firstOrNull { getAttributeName(it) == name }
            ?.let { getAttributeValue(it) }
            ?: ""
    }

    private fun String.toFloatOrDefault(default: Float = 0f): Float {
        return this.toFloatOrNull() ?: default
    }
}
