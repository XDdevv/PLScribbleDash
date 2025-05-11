package zed.rainxch.plscribbledash.presentation.screens.game_modes.one_round.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.domain.model.PaintPath
import zed.rainxch.plscribbledash.domain.model.ParsedPath
import zed.rainxch.plscribbledash.domain.use_case.AddPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.ClearPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.GetGamePathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.GetGameScoreUseCase
import zed.rainxch.plscribbledash.domain.use_case.GetRandomPathDataUseCase
import zed.rainxch.plscribbledash.domain.use_case.RedoPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.UndoPathsUseCase
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.presentation.screens.game_modes.one_round.utils.OneRoundGameState
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val addPathUseCase: AddPathsUseCase,
    private val undoPathUseCase: UndoPathsUseCase,
    private val redoPathUseCase: RedoPathsUseCase,
    private val clearPathsUseCase: ClearPathsUseCase,
    private val getRandomPathDataUseCase: GetRandomPathDataUseCase,
    private val getGameScoreUseCae: GetGameScoreUseCase,
    getPathsUseCase: GetGamePathsUseCase
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

    private var _gameState: MutableStateFlow<OneRoundGameState> = MutableStateFlow(OneRoundGameState.PREVIEW)
    val gameState = _gameState.asStateFlow()

    private var _previewTimer: MutableStateFlow<Int> = MutableStateFlow(3)
    val previewTimer = _previewTimer.asStateFlow()

    private var _countdownTimer: MutableStateFlow<Int> = MutableStateFlow(120)
    val countdownTimer = _countdownTimer.asStateFlow()

    private var _randomPath: MutableStateFlow<ParsedPath?> = MutableStateFlow(null)
    val randomPath = _randomPath.asStateFlow()

    init {
        getRandomPath()
        startGame()
        startPreviewTimer()
    }

    private fun getRandomPath() {
        viewModelScope.launch {
            _randomPath.emit(getRandomPathDataUseCase())
        }
    }

    private fun startGame() {
        viewModelScope.launch {
            delay(3000)
            _gameState.emit(OneRoundGameState.PLAY)
        }
    }

    private fun startPreviewTimer() {
        viewModelScope.launch {
            var counter = 2
            while (counter > 0) {
                delay(1000)
                _previewTimer.emit(counter)
                counter--
            }
        }
    }

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

    fun onFinish(difficultyLevelOption: DifficultyLevelOptions) {
        viewModelScope.launch {
            val score = getGameScoreUseCae(
                userPaths = paths.value,
                exampleParsedPath = randomPath.value ?: ParsedPath(emptyList(), 0, 0, 0f, 0f),
                difficultyLevelOption
            )
            randomPath.value?.let { _gameState.emit(OneRoundGameState.FINISHED(score, it)) }
        }
    }

}