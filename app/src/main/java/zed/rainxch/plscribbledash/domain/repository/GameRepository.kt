package zed.rainxch.plscribbledash.domain.repository

import zed.rainxch.plscribbledash.domain.model.PaintPath
import zed.rainxch.plscribbledash.domain.model.ParsedPath
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions

interface GameRepository {
    fun getPathData(drawableResId: Int): ParsedPath
    suspend fun getResultScore(
        userPaths: List<PaintPath>,
        exampleParsedPath: ParsedPath,
        difficultyLevelOption: DifficultyLevelOptions
    ): Int
}