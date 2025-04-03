package zed.rainxch.plscribbledash.domain.repository

import kotlinx.coroutines.flow.StateFlow
import zed.rainxch.plscribbledash.domain.model.PaintPath

interface PaintRepository {
    val paths: StateFlow<List<PaintPath>>
    val redoPaths: StateFlow<List<PaintPath>>

    fun addPath(path: PaintPath)
    fun undoPath(): Boolean
    fun redoPath(): Boolean
    fun clearPaths()
}