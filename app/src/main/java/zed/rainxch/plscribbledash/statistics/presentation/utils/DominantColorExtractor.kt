package zed.rainxch.plscribbledash.statistics.presentation.utils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DominantColorExtractor(
    private val context: Context
) {

    suspend fun extractDominantColorFromImage(
        @DrawableRes imageRes: Int
    ): Color = withContext(Dispatchers.Default) {
        val drawable = ContextCompat.getDrawable(context, imageRes)
        val bitmap = drawable?.toBitmap()
        bitmap?.let {
            val palette = Palette.from(it).generate()
            val dominantColor = palette.getDominantColor(Color.Gray.toArgb())
            Color(dominantColor).copy(alpha = .1f)
        } ?: Color.Gray
    }
}