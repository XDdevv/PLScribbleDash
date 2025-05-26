package zed.rainxch.plscribbledash.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import zed.rainxch.plscribbledash.main.presentation.components.AppNavigation
import zed.rainxch.plscribbledash.main.presentation.components.BottomNavBar
import zed.rainxch.plscribbledash.main.presentation.vm.MainViewModel
import zed.rainxch.plscribbledash.core.presentation.desingsystem.theme.PLScribbleDashTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()

            val viewModel: MainViewModel = hiltViewModel()
            val snackState = remember { SnackbarHostState() }

            PLScribbleDashTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            navHostController = navHostController,
                        )
                    },
                    snackbarHost = { SnackbarHost(snackState) }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navHostController,
                        snackState = snackState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}