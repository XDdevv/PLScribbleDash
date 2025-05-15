package zed.rainxch.plscribbledash.game.domain.repository

import kotlinx.coroutines.flow.StateFlow
import zed.rainxch.plscribbledash.game.domain.model.PaintPath

interface PaintRepository {
    val paths: StateFlow<List<PaintPath>>
    val redoPaths: StateFlow<List<PaintPath>>

    fun addPath(path: PaintPath)
    fun undoPath(): Boolean
    fun redoPath(): Boolean
    fun clearPaths()
}