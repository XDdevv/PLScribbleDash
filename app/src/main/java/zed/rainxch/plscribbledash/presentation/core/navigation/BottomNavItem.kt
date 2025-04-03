package zed.rainxch.plscribbledash.presentation.core.navigation

import zed.rainxch.plscribbledash.R

data class BottomNavItem(
    val screen: NavGraph,
    val icon: Int,
    val selected: Boolean = false
) {
    companion object {
        fun bottomNavItems() = listOf(
            BottomNavItem(
                screen = NavGraph.Test,
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