package zed.rainxch.plscribbledash.game.domain.model

enum class DifficultyLevelOptions(
    val difficulty: Float,
    val strokeWidthMultiplier: Float,
    val difficultyFactor: Float,
    val scoreMultiplier : Float,
) {
    Beginner(
        difficulty = 15f,
        strokeWidthMultiplier = 10f,
        difficultyFactor = 1.05f,
        scoreMultiplier = .5f
    ),
    Challenging(
        difficulty = 7f,
        strokeWidthMultiplier = 8f,
        difficultyFactor = 1f,
        scoreMultiplier = 1f
    ),
    Master(
        difficulty = 4f,
        strokeWidthMultiplier = 6f,
        difficultyFactor = .95f,
        scoreMultiplier = 1.75f
    )
}