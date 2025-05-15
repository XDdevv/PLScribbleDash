package zed.rainxch.plscribbledash.game.presentation.game.one_round.vm

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
import zed.rainxch.plscribbledash.game.domain.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.game.domain.model.PaintPath
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath
import zed.rainxch.plscribbledash.game.domain.repository.GameRepository
import zed.rainxch.plscribbledash.game.domain.repository.PaintRepository
import zed.rainxch.plscribbledash.game.domain.use_case.GetGamePathsUseCase
import zed.rainxch.plscribbledash.game.domain.use_case.GetRandomPathDataUseCase
import zed.rainxch.plscribbledash.game.presentation.game.one_round.utils.OneRoundGameState
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val paintRepository: PaintRepository,
    private val gameRepository: GameRepository,
    private val getRandomPathDataUseCase: GetRandomPathDataUseCase,
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
            paintRepository.addPath(it)
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
        return paintRepository.undoPath()
    }

    fun onRedo(): Boolean {
        return paintRepository.redoPath()
    }

    fun onClear() {
        paintRepository.clearPaths()
    }

    fun onFinish(difficultyLevelOption: DifficultyLevelOptions) {
        viewModelScope.launch {
            val score = gameRepository.getResultScore(
                userPaths = paths.value,
                exampleParsedPath = randomPath.value ?: ParsedPath(emptyList(), 0, 0, 0f, 0f),
                difficultyLevelOption
            )
            randomPath.value?.let { _gameState.emit(OneRoundGameState.FINISHED(score, it)) }
        }
    }

}