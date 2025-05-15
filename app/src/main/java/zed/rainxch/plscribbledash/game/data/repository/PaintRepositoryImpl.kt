package zed.rainxch.plscribbledash.game.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import zed.rainxch.plscribbledash.game.domain.model.PaintPath
import zed.rainxch.plscribbledash.game.domain.repository.PaintRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaintRepositoryImpl @Inject constructor() : PaintRepository {
    private val _paths = MutableStateFlow<List<PaintPath>>(emptyList())
    override val paths: StateFlow<List<PaintPath>> = _paths.asStateFlow()

    private val _redoPaths = MutableStateFlow<List<PaintPath>>(emptyList())
    override val redoPaths: StateFlow<List<PaintPath>> = _redoPaths.asStateFlow()

    override fun addPath(path: PaintPath) {
        _paths.update {
            _paths.value + path
        }
        _redoPaths.update {
            emptyList()
        }
    }

    override fun undoPath(): Boolean {
        val currentPaths = _paths.value
        if (currentPaths.isEmpty()) return false

        val lastPath = currentPaths.last()
        _redoPaths.update {
            _redoPaths.value + lastPath
        }
        if (_redoPaths.value.size > 5) {
            _redoPaths.value = _redoPaths.value.drop(1)
        }
        _paths.update {
            currentPaths.dropLast(1)
        }
        return true
    }

    override fun redoPath(): Boolean {
        val currentRedoPaths = _redoPaths.value
        if (currentRedoPaths.isEmpty()) return false

        val pathToRedo = currentRedoPaths.last()
        _paths.update {
            _paths.value + pathToRedo
        }
        _redoPaths.update {
            currentRedoPaths.dropLast(1)
        }
        return true
    }

    override fun clearPaths() {
        _paths.update {
            emptyList()
        }
        _redoPaths.update {
            emptyList()
        }
    }
}