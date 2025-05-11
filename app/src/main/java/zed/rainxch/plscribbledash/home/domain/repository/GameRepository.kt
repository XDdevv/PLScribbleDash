package zed.rainxch.plscribbledash.home.domain.repository

import zed.rainxch.plscribbledash.home.domain.model.PaintPath
import zed.rainxch.plscribbledash.home.domain.model.ParsedPath
import zed.rainxch.plscribbledash.home.domain.model.DifficultyLevelOptions

interface GameRepository {
    fun getPathData(drawableResId: Int): ParsedPath
    suspend fun getResultScore(
        userPaths: List<PaintPath>,
        exampleParsedPath: ParsedPath,
        difficultyLevelOption: DifficultyLevelOptions
    ): Int
}