package zed.rainxch.plscribbledash.home.data.datasource

import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.home.domain.model.DifficultyLevelItem
import zed.rainxch.plscribbledash.home.domain.model.DifficultyLevelOptions

fun getDifficultyLevels(): List<DifficultyLevelItem> {
    return listOf(
        DifficultyLevelItem(
            DifficultyLevelOptions.Beginner,
            R.drawable.ic_difficulty_beginner,
            "Beginner"
        ),
        DifficultyLevelItem(
            DifficultyLevelOptions.Challenging,
            R.drawable.ic_difficulty_challenging,
            "Challenging"
        ),
        DifficultyLevelItem(
            DifficultyLevelOptions.Master,
            R.drawable.ic_difficulty_master,
            "Master"
        )
    )
}