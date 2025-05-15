package zed.rainxch.plscribbledash.core.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlinx.serialization.Serializable

@Serializable
sealed class UiText {
    @Serializable
    data class DynamicString(val value: String) : UiText()

    @Serializable
    class StringResource(
        @StringRes val resId: Int,
        val args: List<String> = emptyList()
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> this.value
            is StringResource -> stringResource(id = this.resId, formatArgs = this.args.toTypedArray())
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> this.value
            is StringResource -> context.getString(this.resId, this.args)
        }
    }
}