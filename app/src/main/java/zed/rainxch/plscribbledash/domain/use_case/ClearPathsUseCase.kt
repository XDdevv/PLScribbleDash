package zed.rainxch.plscribbledash.domain.use_case

import zed.rainxch.plscribbledash.domain.repository.PaintRepository
import javax.inject.Inject

class ClearPathsUseCase @Inject constructor(
    private val repository: PaintRepository
) {
    operator fun invoke() {
        repository.clearPaths()
    }
}