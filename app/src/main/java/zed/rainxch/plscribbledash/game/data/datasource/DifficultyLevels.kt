package zed.rainxch.plscribbledash.game.data.datasource

import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.game.domain.model.DifficultyLevelItem
import zed.rainxch.plscribbledash.game.domain.model.DifficultyLevelOptions

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