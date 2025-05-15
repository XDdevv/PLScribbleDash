package zed.rainxch.plscribbledash.main.presentation.models

import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.presentation.navigation.NavGraph

data class BottomNavItem(
    val screen: NavGraph,
    val icon: Int,
    val selected: Boolean = false
) {
    companion object {
        fun bottomNavItems() = listOf(
            BottomNavItem(
                screen = NavGraph.StatisticsScreen,
                icon = R.drawable.ic_analytics
            ),
            BottomNavItem(
                screen = NavGraph.HomeScreen,
                icon = R.drawable.ic_home,
                selected = true
            ),
            BottomNavItem(
                screen = NavGraph.ShopScreen,
                icon = R.drawable.ic_shop,
                selected = false
            )
        )
    }
}