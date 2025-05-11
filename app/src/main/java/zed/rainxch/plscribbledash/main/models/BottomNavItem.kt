package zed.rainxch.plscribbledash.main.models

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
                screen = NavGraph.Statistics,
                icon = R.drawable.ic_analytics
            ),
            BottomNavItem(
                screen = NavGraph.Home,
                icon = R.drawable.ic_home,
                selected = true
            )
        )
    }
}