package zed.rainxch.plscribbledash.domain.use_case

import zed.rainxch.plscribbledash.domain.model.PaintPath
import zed.rainxch.plscribbledash.domain.repository.PaintRepository
import javax.inject.Inject

class AddPathsUseCase @Inject constructor(
    private val repository: PaintRepository
) {
    operator fun invoke(path: PaintPath) {
        repository.addPath(path)
    }
}