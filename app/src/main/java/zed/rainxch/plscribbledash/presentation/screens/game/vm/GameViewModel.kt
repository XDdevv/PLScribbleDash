package zed.rainxch.plscribbledash.presentation.screens.game.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import zed.rainxch.plscribbledash.domain.model.PaintPath
import zed.rainxch.plscribbledash.domain.use_case.AddPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.ClearPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.GetPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.RedoPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.UndoPathsUseCase
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val addPathUseCase: AddPathsUseCase,
    private val undoPathUseCase: UndoPathsUseCase,
    private val redoPathUseCase: RedoPathsUseCase,
    private val clearPathsUseCase: ClearPathsUseCase,
    getPathsUseCase: GetPathsUseCase
) : ViewModel() {

    val paths: StateFlow<List<PaintPath>> = getPathsUseCase()
    val redoPaths: StateFlow<List<PaintPath>> = getPathsUseCase.getRepoPaths()

    private val _currentPath = mutableStateOf<PaintPath?>(null)
    val currentPath: State<PaintPath?> = _currentPath

    private val _currentColor = mutableStateOf<Color>(Color.Black)
    val currentColor: State<Color> = _currentColor

    private val _strokeWidth = mutableFloatStateOf(10f)
    val strokeWidth: State<Float> = _strokeWidth

    private val _isDrawing = mutableStateOf(false)

    fun onTouchStart(offset: Offset) {
        _isDrawing.value = true
        _currentPath.value = PaintPath(
            points = listOf(offset),
            color = _currentColor.value,
            strokeWidth = _strokeWidth.floatValue
        )
    }

    fun onTouchMove(offset: Offset) {
        if (!_isDrawing.value) return

        _currentPath.value = _currentPath.value?.let {
            it.copy(points = it.points + offset)
        }
    }

    fun onTouchEnd() {
        _isDrawing.value = false
        _currentPath.value?.let {
            addPathUseCase(it)
        }
        _currentPath.value = null
    }

    fun onColorChange(color: Color) {
        _currentColor.value = color
    }

    fun onStrokeWidthChange(width: Float) {
        _strokeWidth.floatValue = width
    }

    fun onUndo(): Boolean {
        return undoPathUseCase()
    }

    fun onRedo(): Boolean {
        return redoPathUseCase()
    }

    fun onClear() {
        clearPathsUseCase()
    }

}