package zed.rainxch.plscribbledash.game.presentation.game.speed_draw.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import zed.rainxch.plscribbledash.game.domain.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.game.domain.model.PaintPath
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath
import zed.rainxch.plscribbledash.game.domain.repository.GameRepository
import zed.rainxch.plscribbledash.game.domain.repository.PaintRepository
import zed.rainxch.plscribbledash.game.domain.use_case.CheckSpeedAverageAccuracyUseCase
import zed.rainxch.plscribbledash.game.domain.use_case.CheckMehPlusScoreUseCase
import zed.rainxch.plscribbledash.game.domain.use_case.GetGamePathsUseCase
import zed.rainxch.plscribbledash.game.domain.use_case.GetRandomPathDataUseCase
import zed.rainxch.plscribbledash.game.presentation.game.speed_draw.utils.SpeedGameState
import zed.rainxch.plscribbledash.game.presentation.game.speed_draw.utils.TimeUI
import javax.inject.Inject

@HiltViewModel
class SpeedGameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val paintRepository: PaintRepository,
    private val getRandomPathDataUseCase: GetRandomPathDataUseCase,
    private val checkHighestMehPlusUseCase: CheckMehPlusScoreUseCase,
    private val checkAverageAccuracyUseCase: CheckSpeedAverageAccuracyUseCase,
    getPathsUseCase: GetGamePathsUseCase
) : ViewModel() {

    // DRAWING SPECIFIC STATES

    val paths: StateFlow<List<PaintPath>> = getPathsUseCase()
    val redoPaths: StateFlow<List<PaintPath>> = getPathsUseCase.getRepoPaths()

    private val _currentPath = mutableStateOf<PaintPath?>(null)
    val currentPath: State<PaintPath?> = _currentPath

    private val _currentColor = mutableStateOf<Color>(Color.Black)
    val currentColor: State<Color> = _currentColor

    private val _strokeWidth = mutableFloatStateOf(10f)
    val strokeWidth: State<Float> = _strokeWidth

    private val _isDrawing = mutableStateOf(false)

    // GAME STATE
    private var _gameState: MutableStateFlow<SpeedGameState> =
        MutableStateFlow(SpeedGameState.PREVIEW)
    val gameState = _gameState.asStateFlow()

    private var _randomPath: MutableStateFlow<ParsedPath?> = MutableStateFlow(null)
    val randomPath = _randomPath.asStateFlow()

    private var _mehPlusCounter: MutableStateFlow<Int> = MutableStateFlow(0)
    var mehPlusCounter = _mehPlusCounter.asStateFlow()

    private var _totalPercentage: MutableStateFlow<Int> = MutableStateFlow(0)

    private var _totalCounter: MutableStateFlow<Int> = MutableStateFlow(0)

    private var _averageAccuracy: MutableStateFlow<Int> = MutableStateFlow(0)

    // PREVIEW TIMER
    private var _previewTimer: MutableStateFlow<Int> = MutableStateFlow(3)
    val previewTimer = _previewTimer.asStateFlow()

    // COUNTDOWN TIMER
    private var _remainingCountdownSeconds: MutableStateFlow<Int> = MutableStateFlow(120)
    val countdownTimer = _remainingCountdownSeconds
        .map { it.toTimeUI() }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.Lazily,
            TimeUI(120, "2:00")
        )
    private var timerJob: Job? = null

    init {
        getRandomPath()
        startGame()
        startTimer()
    }

    fun handleCountdownTimer() {
        viewModelScope.launch {
            _gameState.collectLatest { state ->
                when (state) {
                    is SpeedGameState.PLAY -> {
                        startCountdown()
                    }

                    else -> {
                        pauseCountdown()
                    }
                }
            }
        }
    }

    private fun startCountdown() {
        if (timerJob?.isActive == true) return
        timerJob = viewModelScope.launch {
            while (_remainingCountdownSeconds.value > 0 && isActive) {
                delay(1000)
                _remainingCountdownSeconds.value -= 1
            }

            if (_remainingCountdownSeconds.value == 0) {
                onFinish()
            }
        }
    }

    private fun pauseCountdown() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun getRandomPath() {
        viewModelScope.launch {
            _randomPath.emit(getRandomPathDataUseCase())
        }
    }

    private fun startGame() {
        viewModelScope.launch {
            delay(3000)
            _gameState.emit(SpeedGameState.PLAY)
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            var counter = 3
            while (counter > 0) {
                _previewTimer.emit(counter)
                delay(1000)
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

    fun onFinish() {
        viewModelScope.launch {

            _gameState.emit(
                SpeedGameState.FINISHED(
                    _averageAccuracy.value,
                    _mehPlusCounter.value,
                    checkHighestMehPlusUseCase(_mehPlusCounter.value),
                    checkAverageAccuracyUseCase(_averageAccuracy.value),
                )
            )
        }
    }

    fun handleContinue(difficultyLevelOption: DifficultyLevelOptions) {
        viewModelScope.launch {
            val score = gameRepository.getResultScore(
                userPaths = paths.value,
                exampleParsedPath = randomPath.value ?: ParsedPath(emptyList(), 0, 0, 0f, 0f),
                difficultyLevelOption
            )
            onClear()
            _gameState.emit(SpeedGameState.PREVIEW)
            getRandomPath()
            startGame()
            startTimer()
            if (score >= 40) {
                _mehPlusCounter.value = _mehPlusCounter.value + 1
            }
            _totalPercentage.value = _totalPercentage.value + score
            _totalCounter.value = _totalCounter.value + 1
            _averageAccuracy.value = _totalPercentage.value / _totalCounter.value
        }
    }

    fun onStateChanged(state: SpeedGameState) {
        viewModelScope.launch {
            _gameState.emit(state)
        }
    }

}

private fun Int.toTimeUI(): TimeUI {
    return TimeUI(
        this,
        this.toMinuteAndSecond()
    )
}

private fun Int.toMinuteAndSecond(): String {
    val minutes = this / 60
    val seconds = this % 60
    return "${minutes.toString().padStart(1, '0')} : ${seconds.toString().padStart(2, '0')}"
}
