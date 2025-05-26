package zed.rainxch.plscribbledash.game.domain.repository

import zed.rainxch.plscribbledash.game.domain.model.PaintPath
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath
import zed.rainxch.plscribbledash.game.domain.model.DifficultyLevelOptions

interface GameRepository {
    suspend fun getResultScore(
        userPaths: List<PaintPath>,
        exampleParsedPath: ParsedPath,
        difficultyLevelOption: DifficultyLevelOptions
    ): Int

    suspend fun earnCoin(score: Int, mode: DifficultyLevelOptions) : Int
}