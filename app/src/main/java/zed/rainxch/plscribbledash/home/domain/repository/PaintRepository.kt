package zed.rainxch.plscribbledash.home.domain.repository

import kotlinx.coroutines.flow.StateFlow
import zed.rainxch.plscribbledash.home.domain.model.PaintPath

interface PaintRepository {
    val paths: StateFlow<List<PaintPath>>
    val redoPaths: StateFlow<List<PaintPath>>

    fun addPath(path: PaintPath)
    fun undoPath(): Boolean
    fun redoPath(): Boolean
    fun clearPaths()
}