package zed.rainxch.plscribbledash.core.data.datasource.static_

import androidx.compose.ui.graphics.Color
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

val shopPenList = listOf(
    ShopPen.Basic(Color(0xFF101820), 25, true, true, penName = "black"),
    ShopPen.Basic(Color(0xFFB22234), penBought = true, penName = "red"),
    ShopPen.Basic(Color(0xFFF9D85D), penName = "yellow"),
    ShopPen.Basic(Color(0xFF1D4E89), penName = "blue"),
    ShopPen.Basic(Color(0xFF4CAF50), penName = "green"),
    ShopPen.Basic(Color(0xFFF57F20), penName = "brown"),

    ShopPen.Premium(Color(0xFFF4A6B8), penName = "pink"),
    ShopPen.Premium(Color(0xFF6A0FAB), penName = "purple"),
    ShopPen.Premium(Color(0xFF008C92), penName = "cyan"),
    ShopPen.Premium(Color(0xFFFFD700), penName = "yelowish"),
    ShopPen.Premium(Color(0xFFFF6F61), penName = "redish"),
    ShopPen.Premium(Color(0xFF4B0082), penName = "purplish"),
    ShopPen.Premium(Color(0xFFB87333), penName = "brownish"),

    ShopPen.Legendary(
        colors = listOf(
            Color(0xFFFB02FB), // Magenta
            Color(0xFF0000FF), // Blue
            Color(0xFF00EEFF), // Cyan
            Color(0xFF008000), // Green
            Color(0xFFFFFF00), // Yellow
            Color(0xFFFFA500), // Orange
            Color(0xFFFF0000), // Red
        ),
        penName = "legendary_gradient"
    )
)