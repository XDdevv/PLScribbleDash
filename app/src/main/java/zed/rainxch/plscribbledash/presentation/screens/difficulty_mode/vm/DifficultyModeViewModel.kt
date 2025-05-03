package zed.rainxch.plscribbledash.presentation.screens.difficulty_mode.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelItem
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import javax.inject.Inject

@HiltViewModel
class DifficultyModeViewModel @Inject constructor() : ViewModel() {

    fun getDifficultyLevels(): List<DifficultyLevelItem> {
        return listOf(
            DifficultyLevelItem(DifficultyLevelOptions.Beginner, R.drawable.ic_difficulty_beginner, "Beginner"),
            DifficultyLevelItem(DifficultyLevelOptions.Challenging, R.drawable.ic_difficulty_challenging, "Challenging"),
            DifficultyLevelItem(DifficultyLevelOptions.Master, R.drawable.ic_difficulty_master, "Master")
        )
    }
}