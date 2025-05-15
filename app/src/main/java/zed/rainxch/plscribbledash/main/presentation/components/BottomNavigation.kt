package zed.rainxch.plscribbledash.main.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import zed.rainxch.plscribbledash.main.presentation.models.BottomNavItem
import zed.rainxch.plscribbledash.core.presentation.navigation.NavGraph

@Composable
fun BottomNavBar(
    navHostController: NavHostController
) {
    val bottomNavAllowedScreenList = listOf(
        NavGraph.HomeScreen,
        NavGraph.StatisticsScreen,
        NavGraph.ShopScreen,
    )
    val currentStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = currentStackEntry?.destination

    val screenAllowed = currentDestination != null && bottomNavAllowedScreenList.any { allowedScreen ->
        currentDestination.route?.endsWith(".${allowedScreen::class.simpleName}") == true ||
                currentDestination.route == allowedScreen.route
    }

    if (screenAllowed) {
        NavigationBar(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            BottomNavItem.Companion.bottomNavItems().forEach { navItem ->
                val isSelected = when {
                    currentDestination.route?.endsWith(".${navItem.screen::class.simpleName}") == true -> true
                    currentDestination.route == navItem.screen.route -> true
                    else -> false
                }
                val itemColor = if (isSelected) {
                    when(navItem.screen.route) {
                        "home" -> Color(0xff238CFF)
                        "statistics" -> Color(0xffFA852C)
                        "shop" -> Color(0xffAB5CFA)
                        else -> Color.Gray
                    }
                } else {
                    Color(0xffE1D5CA)
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
                        Icon(
                            imageVector = ImageVector.vectorResource(navItem.icon),
                            contentDescription = "Bottom nav item",
                            tint = itemColor,
                            modifier = Modifier.size(26.dp)
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