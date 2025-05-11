package zed.rainxch.plscribbledash.presentation.screens.game_modes.endless.vm

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
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.domain.model.PaintPath
import zed.rainxch.plscribbledash.domain.model.ParsedPath
import zed.rainxch.plscribbledash.domain.use_case.AddPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.CheckEndlessAverageAccuracyUseCase
import zed.rainxch.plscribbledash.domain.use_case.CheckGoodPlusScoreUseCase
import zed.rainxch.plscribbledash.domain.use_case.ClearPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.GetGamePathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.GetGameScoreUseCase
import zed.rainxch.plscribbledash.domain.use_case.GetRandomPathDataUseCase
import zed.rainxch.plscribbledash.domain.use_case.RedoPathsUseCase
import zed.rainxch.plscribbledash.domain.use_case.UndoPathsUseCase
import zed.rainxch.plscribbledash.presentation.core.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.presentation.screens.game_modes.endless.utils.EndlessGameState
import zed.rainxch.plscribbledash.presentation.screens.game_modes.speed_draw.utils.SpeedGameState
import javax.inject.Inject

@HiltViewModel
class EndlessGameViewModel @Inject constructor(
    private val addPathUseCase: AddPathsUseCase,
    private val undoPathUseCase: UndoPathsUseCase,
    private val redoPathUseCase: RedoPathsUseCase,
    private val clearPathsUseCase: ClearPathsUseCase,
    private val getRandomPathDataUseCase: GetRandomPathDataUseCase,
    private val getGameScoreUseCae: GetGameScoreUseCase,
    private val checkHighestGoodPlusUseCase: CheckGoodPlusScoreUseCase,
    private val checkAverageAccuracyUseCase: CheckEndlessAverageAccuracyUseCase,
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

    private var _gameState: MutableStateFlow<EndlessGameState> =
        MutableStateFlow(EndlessGameState.PREVIEW)
    val gameState = _gameState.asStateFlow()

    private var _previewTimer: MutableStateFlow<Int> = MutableStateFlow(3)
    val previewTimer = _previewTimer.asStateFlow()

    private var _goodPaintCounter: MutableStateFlow<Int> = MutableStateFlow(0)
    val paintCounter = _goodPaintCounter.asStateFlow()

    private var _totalPercentage: MutableStateFlow<Int> = MutableStateFlow(0)

    private var _totalCounter: MutableStateFlow<Int> = MutableStateFlow(0)

    private var _averageAccuracy: MutableStateFlow<Int> = MutableStateFlow(0)

    private var _randomPath: MutableStateFlow<ParsedPath?> = MutableStateFlow(null)
    val randomPath = _randomPath.asStateFlow()

    init {
        getRandomPath()
        startGame()
        startTimer()
    }

    private fun getRandomPath() {
        viewModelScope.launch {
            _randomPath.emit(getRandomPathDataUseCase())
        }
    }

    private fun startGame() {
        viewModelScope.launch {
            delay(3000)
            _gameState.emit(EndlessGameState.PLAY)
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

    fun onFinishClick() {
        viewModelScope.launch {
            _gameState.emit(
                EndlessGameState.FINISHED(
                    _averageAccuracy.value,
                    _goodPaintCounter.value,
                    checkHighestGoodPlusUseCase(_goodPaintCounter.value),
                    checkAverageAccuracyUseCase(_averageAccuracy.value),
                )
            )
        }
    }

    fun onNextDrawingClick() {
        viewModelScope.launch {
            onClear()
            _gameState.emit(EndlessGameState.PREVIEW)
            getRandomPath()
            startGame()
            startTimer()
        }
    }

    fun onDoneClick(difficultyLevelOption: DifficultyLevelOptions) {
        viewModelScope.launch {
            val score = getGameScoreUseCae(
                userPaths = paths.value,
                exampleParsedPath = randomPath.value ?: ParsedPath(emptyList(), 0, 0, 0f, 0f),
                difficultyLevelOption = difficultyLevelOption
            )
            _randomPath.value?.let { randomPath ->
                _gameState.emit(
                    EndlessGameState.RESULT(
                        score = score,
                        previewPaths = randomPath,
                        userDrawnPath = paths.value
                    )
                )
            }
            if (isSuccess(score)) {
                _goodPaintCounter.value = _goodPaintCounter.value + 1
            }
            _totalPercentage.value = _totalPercentage.value + score
            _totalCounter.value = _totalCounter.value + 1
            _averageAccuracy.value = _totalPercentage.value / _totalCounter.value
        }
    }

    fun isSuccess(rate: Int): Boolean {
        return rate in 70..100
    }

    fun getRandomTitle(rate: Int): String {
        return when (rate) {
            in 0..40 -> "Oops"
            in 40..70 -> "Meh"
            in 70..80 -> "Good"
            in 80..90 -> "Great"
            in 90..100 -> "Woohoo!"
            else -> "Invalid"
        }
    }

    fun getRandomFeedbackResource(rate: Int): Int {
        return when (rate) {
            in 0..40 -> getOopsFeedback().random()
            in 40..70 -> getGoodFeedback().random()
            in 70..79 -> getGoodFeedback().random()
            in 80..90 -> getGoodFeedback().random()
            in 100..100 -> getWoohoFeedback().random()
            else -> getOopsFeedback().random()
        }
    }

    private fun getWoohoFeedback(): List<Int> = listOf(
        R.string.feedback_woohoo_1,
        R.string.feedback_woohoo_2,
        R.string.feedback_woohoo_3,
        R.string.feedback_woohoo_4,
        R.string.feedback_woohoo_5,
        R.string.feedback_woohoo_6,
        R.string.feedback_woohoo_7,
        R.string.feedback_woohoo_8,
        R.string.feedback_woohoo_9,
        R.string.feedback_woohoo_10,
    )

    private fun getGoodFeedback(): List<Int> = listOf(
        R.string.feedback_good_1,
        R.string.feedback_good_2,
        R.string.feedback_good_3,
        R.string.feedback_good_4,
        R.string.feedback_good_5,
        R.string.feedback_good_6,
        R.string.feedback_good_7,
        R.string.feedback_good_8,
        R.string.feedback_good_9,
        R.string.feedback_good_10,
    )

    private fun getOopsFeedback(): List<Int> = listOf(
        R.string.feedback_oops_1,
        R.string.feedback_oops_2,
        R.string.feedback_oops_3,
        R.string.feedback_oops_4,
        R.string.feedback_oops_5,
        R.string.feedback_oops_6,
        R.string.feedback_oops_7,
        R.string.feedback_oops_8,
        R.string.feedback_oops_9,
        R.string.feedback_oops_10,
    )

}