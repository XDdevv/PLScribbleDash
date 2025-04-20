package zed.rainxch.plscribbledash.domain.use_case

import zed.rainxch.plscribbledash.domain.model.PaintPath
import zed.rainxch.plscribbledash.domain.model.ParsedPath
import zed.rainxch.plscribbledash.domain.repository.GameRepository
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import javax.inject.Inject

class GetGameScoreUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(
        userPaths: List<PaintPath>,
        exampleParsedPath: ParsedPath,
        difficultyLevelOption: DifficultyLevelOptions
    ): Int {
        return gameRepository.getResultScore(
            userPaths,
            exampleParsedPath,
            difficultyLevelOption
        )
    }
}