package zed.rainxch.plscribbledash.game.domain.model

enum class DifficultyLevelOptions(val difficulty: Float, val strokeWidthMultiplier: Float, val difficultyFactor : Float) {
    Beginner(
        difficulty = 15f,
        strokeWidthMultiplier = 10f,
        difficultyFactor = 1.05f
    ),
    Challenging(
        difficulty = 7f,
        strokeWidthMultiplier = 8f,
        difficultyFactor = 1f
    ),
    Master(
        difficulty = 4f,
        strokeWidthMultiplier = 6f,
        difficultyFactor = .95f
    )
}