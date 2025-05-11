package zed.rainxch.plscribbledash.home.domain.use_case

import kotlinx.coroutines.flow.StateFlow
import zed.rainxch.plscribbledash.home.domain.model.PaintPath
import zed.rainxch.plscribbledash.home.domain.repository.PaintRepository
import javax.inject.Inject

class GetGamePathsUseCase @Inject constructor(
    private val repository: PaintRepository
) {
    operator fun invoke(): StateFlow<List<PaintPath>> {
        return repository.paths
    }

    fun getRepoPaths(): StateFlow<List<PaintPath>> {
        return repository.redoPaths
    }
}