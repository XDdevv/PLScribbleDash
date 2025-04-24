package zed.rainxch.plscribbledash.presentation.screens.result.vm

import androidx.lifecycle.ViewModel
import zed.rainxch.plscribbledash.R

class ResultViewModel : ViewModel() {
    fun getRandomTitle(rate: Int): String {
        return when (rate) {
            in 0..40 -> "Oops"
            in 40..70 -> "Meh"
            in 70..79 -> "Good"
            in 80..90 -> "Great"
            in 90..100 -> "Woohoo!"
            else -> "Invalid"
        }
    }

    fun getRandomFeedbackResource(rate: Int): Int {
        return when (rate) {
            in 0..40 -> getOopsFeedback().random()
            in 40..70 -> getGoodFeedback().random()
            in 70..79 -> getGoodFeedback().random()
            in 80..90 -> getGoodFeedback().random()
            in 100..100 -> getWoohoFeedback().random()
            else -> getOopsFeedback().random()
        }
    }

    private fun getWoohoFeedback(): List<Int> = listOf(
        R.string.feedback_woohoo_1,
        R.string.feedback_woohoo_2,
        R.string.feedback_woohoo_3,
        R.string.feedback_woohoo_4,
        R.string.feedback_woohoo_5,
        R.string.feedback_woohoo_6,
        R.string.feedback_woohoo_7,
        R.string.feedback_woohoo_8,
        R.string.feedback_woohoo_9,
        R.string.feedback_woohoo_10,
    )

    private fun getGoodFeedback(): List<Int> = listOf(
        R.string.feedback_good_1,
        R.string.feedback_good_2,
        R.string.feedback_good_3,
        R.string.feedback_good_4,
        R.string.feedback_good_5,
        R.string.feedback_good_6,
        R.string.feedback_good_7,
        R.string.feedback_good_8,
        R.string.feedback_good_9,
        R.string.feedback_good_10,
    )

    private fun getOopsFeedback(): List<Int> = listOf(
        R.string.feedback_oops_1,
        R.string.feedback_oops_2,
        R.string.feedback_oops_3,
        R.string.feedback_oops_4,
        R.string.feedback_oops_5,
        R.string.feedback_oops_6,
        R.string.feedback_oops_7,
        R.string.feedback_oops_8,
        R.string.feedback_oops_9,
        R.string.feedback_oops_10,
    )

}