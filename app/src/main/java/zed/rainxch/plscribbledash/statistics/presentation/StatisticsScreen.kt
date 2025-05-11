package zed.rainxch.plscribbledash.statistics.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import zed.rainxch.plscribbledash.core.presentation.components.LabelXLargeText
import zed.rainxch.plscribbledash.statistics.presentation.components.StatisticsItem
import zed.rainxch.plscribbledash.statistics.presentation.vm.StatisticsViewModel

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val statistics by viewModel.statisticsState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        LabelXLargeText(
            text = "Statistics",
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(18.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(statistics) { statistic ->
                StatisticsItem(
                    statistic = statistic,
                )
            }
        }
    }
}