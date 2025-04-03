package zed.rainxch.plscribbledash.presentation.screens.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import zed.rainxch.plscribbledash.presentation.core.navigation.BottomNavItem
import zed.rainxch.plscribbledash.presentation.core.navigation.NavGraph

@Composable
fun BottomNavBar(
    navHostController: NavHostController,
    bottomNavAllowedScreenList: List<NavGraph>,
    modifier: Modifier = Modifier
) {
    val currentStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = currentStackEntry?.destination

    val allowedScreens = currentDestination != null && bottomNavAllowedScreenList.any { allowedScreen ->
        currentDestination.route?.endsWith(".${allowedScreen::class.simpleName}") == true ||
                currentDestination.route == allowedScreen.route
    }

    if (allowedScreens) {
        NavigationBar(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            BottomNavItem.bottomNavItems().forEach { navItem ->
                val isSelected = when {
                    currentDestination.route?.endsWith(".${navItem.screen::class.simpleName}") == true -> true
                    currentDestination.route == navItem.screen.route -> true
                    else -> false
                }

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navHostController.navigate(navItem.screen) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Image(
                            painter = painterResource(navItem.icon),
                            contentDescription = "Bottom nav item",
                            colorFilter = ColorFilter.tint(
                                if (isSelected) Color(0xFF238CFF)
                                else Color(0xffE1D5CA)
                            ),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                    )
                )
            }
        }
    }
}