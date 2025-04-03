package zed.rainxch.plscribbledash.presentation.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import zed.rainxch.plscribbledash.presentation.core.ui.theme.PLScribbleDashTheme
import zed.rainxch.plscribbledash.presentation.screens.main.components.AppNavigation
import zed.rainxch.plscribbledash.presentation.screens.main.components.BottomNavBar
import zed.rainxch.plscribbledash.presentation.screens.main.vm.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            val viewModel : MainViewModel = hiltViewModel()
            PLScribbleDashTheme {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            navHostController = navHostController,
                            bottomNavAllowedScreenList = viewModel.bottomNavAllowedScreenList()
                        )
                    }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navHostController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}