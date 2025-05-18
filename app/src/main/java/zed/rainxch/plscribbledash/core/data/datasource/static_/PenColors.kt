package zed.rainxch.plscribbledash.core.data.datasource.static_

import androidx.compose.ui.graphics.Color
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

val shopPenList = listOf(
    ShopPen.Basic(Color(0xFF101820), 25, true, true),
    ShopPen.Basic(Color(0xFFB22234)),
    ShopPen.Basic(Color(0xFFF9D85D)),
    ShopPen.Basic(Color(0xFF1D4E89)),
    ShopPen.Basic(Color(0xFF4CAF50)),
    ShopPen.Basic(Color(0xFFF57F20)),

    ShopPen.Premium(Color(0xFFF4A6B8)),
    ShopPen.Premium(Color(0xFF6A0FAB)),
    ShopPen.Premium(Color(0xFF008C92)),
    ShopPen.Premium(Color(0xFFFFD700)),
    ShopPen.Premium(Color(0xFFFF6F61)),
    ShopPen.Premium(Color(0xFF4B0082)),
    ShopPen.Premium(Color(0xFFB87333)),

    ShopPen.Legendary(
        colors = listOf(
            Color(0xFFFB02FB), // Magenta
            Color(0xFF0000FF), // Blue
            Color(0xFF00EEFF), // Cyan
            Color(0xFF008000), // Green
            Color(0xFFFFFF00), // Yellow
            Color(0xFFFFA500), // Orange
            Color(0xFFFF0000), // Red
        )
    )
)