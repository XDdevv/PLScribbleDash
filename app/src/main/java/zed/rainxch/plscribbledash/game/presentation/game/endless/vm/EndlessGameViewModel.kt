package zed.rainxch.plscribbledash.game.presentation.game.endless.vm

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
import zed.rainxch.plscribbledash.core.presentation.utils.UiText
import zed.rainxch.plscribbledash.game.domain.model.DifficultyLevelOptions
import zed.rainxch.plscribbledash.game.domain.model.PaintPath
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath
import zed.rainxch.plscribbledash.game.domain.repository.GameRepository
import zed.rainxch.plscribbledash.game.domain.repository.PaintRepository
import zed.rainxch.plscribbledash.game.domain.use_case.CheckEndlessAverageAccuracyUseCase
import zed.rainxch.plscribbledash.game.domain.use_case.CheckGoodPlusScoreUseCase
import zed.rainxch.plscribbledash.game.domain.use_case.GetGamePathsUseCase
import zed.rainxch.plscribbledash.game.domain.use_case.GetRandomPathDataUseCase
import zed.rainxch.plscribbledash.game.presentation.game.endless.utils.EndlessGameState
import javax.inject.Inject

@HiltViewModel
class EndlessGameViewModel @Inject constructor(
    private val paintRepository: PaintRepository,
    private val gameRepository: GameRepository,
    private val getRandomPathDataUseCase: GetRandomPathDataUseCase,
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
            paintRepository.addPath(it)
        }
        _currentPath.value = null
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
            val score = gameRepository.getResultScore(
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

    internal fun isSuccess(rate: Int): Boolean {
        return rate in 60..100
    }

    fun getCheckboxImageId(score: Int): Int {
        return if(isSuccess(score)) R.drawable.ic_check_on else R.drawable.ic_check_off
    }

    fun getRandomTitle(rate: Int): UiText {
        return when (rate) {
            in 0..40 -> UiText.StringResource(R.string.oops)
            in 41..70 -> UiText.StringResource(R.string.meh)
            in 71..80 -> UiText.StringResource(R.string.good)
            in 81..90 -> UiText.StringResource(R.string.great)
            in 91..100 -> UiText.StringResource(R.string.woohoo)
            else -> UiText.StringResource(R.string.invalid)
        }
    }

    fun getRandomFeedback(rate: Int): UiText {
        return when (rate) {
            in 0..40 -> getOopsFeedback().random()
            in 40..70 -> getGoodFeedback().random()
            in 70..79 -> getGoodFeedback().random()
            in 80..90 -> getGoodFeedback().random()
            in 100..100 -> getWoohooFeedback().random()
            else -> getOopsFeedback().random()
        }
    }

    private fun getWoohooFeedback(): List<UiText> = listOf(
        UiText.StringResource(R.string.feedback_woohoo_1),
        UiText.StringResource(R.string.feedback_woohoo_2),
        UiText.StringResource(R.string.feedback_woohoo_3),
        UiText.StringResource(R.string.feedback_woohoo_4),
        UiText.StringResource(R.string.feedback_woohoo_5),
        UiText.StringResource(R.string.feedback_woohoo_6),
        UiText.StringResource(R.string.feedback_woohoo_7),
        UiText.StringResource(R.string.feedback_woohoo_8),
        UiText.StringResource(R.string.feedback_woohoo_9),
        UiText.StringResource(R.string.feedback_woohoo_10),
    )

    private fun getGoodFeedback(): List<UiText> = listOf(
        UiText.StringResource(R.string.feedback_good_1),
        UiText.StringResource(R.string.feedback_good_2),
        UiText.StringResource(R.string.feedback_good_3),
        UiText.StringResource(R.string.feedback_good_4),
        UiText.StringResource(R.string.feedback_good_5),
        UiText.StringResource(R.string.feedback_good_6),
        UiText.StringResource(R.string.feedback_good_7),
        UiText.StringResource(R.string.feedback_good_8),
        UiText.StringResource(R.string.feedback_good_9),
        UiText.StringResource(R.string.feedback_good_10),
    )

    private fun getOopsFeedback(): List<UiText> = listOf(
        UiText.StringResource(R.string.feedback_oops_1),
        UiText.StringResource(R.string.feedback_oops_2),
        UiText.StringResource(R.string.feedback_oops_3),
        UiText.StringResource(R.string.feedback_oops_4),
        UiText.StringResource(R.string.feedback_oops_5),
        UiText.StringResource(R.string.feedback_oops_6),
        UiText.StringResource(R.string.feedback_oops_7),
        UiText.StringResource(R.string.feedback_oops_8),
        UiText.StringResource(R.string.feedback_oops_9),
        UiText.StringResource(R.string.feedback_oops_10),
    )

}