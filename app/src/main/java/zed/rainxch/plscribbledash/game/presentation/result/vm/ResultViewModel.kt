package zed.rainxch.plscribbledash.game.presentation.result.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen
import zed.rainxch.plscribbledash.core.domain.repository.PlayerRepository
import zed.rainxch.plscribbledash.core.presentation.utils.UiText
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {
    var canvasBackground by mutableStateOf<ShopCanvas>(ShopCanvas.Basic(Color.White))
    var penColor by mutableStateOf<ShopPen>(ShopPen.Basic(Color.Black))

    init {
        getEquippedCanvas()
        getEquippedPen()
    }
    fun getEquippedCanvas()  {
        viewModelScope.launch {
            canvasBackground = playerRepository.getEquippedCanvas()
        }
    }

    fun getEquippedPen()  {
        viewModelScope.launch {
            penColor = playerRepository.getEquippedPen()
        }
    }


    fun getRandomTitle(rate: Int): UiText {
        return when (rate) {
            in 0..40 -> UiText.StringResource(R.string.oops)
            in 41..70 -> UiText.StringResource(R.string.meh)
            in 71..80 -> UiText.StringResource(R.string.good)
            in 81..90 -> UiText.StringResource(R.string.great)
            in 91..100 -> UiText.StringResource(R.string.woohoo)
            else -> UiText.StringResource(R.string.invalid)
        }
    }

    fun getRandomFeedback(rate: Int): UiText {
        return when (rate) {
            in 0..40 -> getOopsFeedback().random()
            in 40..70 -> getGoodFeedback().random()
            in 70..79 -> getGoodFeedback().random()
            in 80..90 -> getGoodFeedback().random()
            in 100..100 -> getWoohooFeedback().random()
            else -> getOopsFeedback().random()
        }
    }

    private fun getWoohooFeedback(): List<UiText> = listOf(
        UiText.StringResource(R.string.feedback_woohoo_1),
        UiText.StringResource(R.string.feedback_woohoo_2),
        UiText.StringResource(R.string.feedback_woohoo_3),
        UiText.StringResource(R.string.feedback_woohoo_4),
        UiText.StringResource(R.string.feedback_woohoo_5),
        UiText.StringResource(R.string.feedback_woohoo_6),
        UiText.StringResource(R.string.feedback_woohoo_7),
        UiText.StringResource(R.string.feedback_woohoo_8),
        UiText.StringResource(R.string.feedback_woohoo_9),
        UiText.StringResource(R.string.feedback_woohoo_10),
    )

    private fun getGoodFeedback(): List<UiText> = listOf(
        UiText.StringResource(R.string.feedback_good_1),
        UiText.StringResource(R.string.feedback_good_2),
        UiText.StringResource(R.string.feedback_good_3),
        UiText.StringResource(R.string.feedback_good_4),
        UiText.StringResource(R.string.feedback_good_5),
        UiText.StringResource(R.string.feedback_good_6),
        UiText.StringResource(R.string.feedback_good_7),
        UiText.StringResource(R.string.feedback_good_8),
        UiText.StringResource(R.string.feedback_good_9),
        UiText.StringResource(R.string.feedback_good_10),
    )

    private fun getOopsFeedback(): List<UiText> = listOf(
        UiText.StringResource(R.string.feedback_oops_1),
        UiText.StringResource(R.string.feedback_oops_2),
        UiText.StringResource(R.string.feedback_oops_3),
        UiText.StringResource(R.string.feedback_oops_4),
        UiText.StringResource(R.string.feedback_oops_5),
        UiText.StringResource(R.string.feedback_oops_6),
        UiText.StringResource(R.string.feedback_oops_7),
        UiText.StringResource(R.string.feedback_oops_8),
        UiText.StringResource(R.string.feedback_oops_9),
        UiText.StringResource(R.string.feedback_oops_10),
    )
}