package zed.rainxch.plscribbledash.core.data.datasource.static_

import androidx.compose.ui.graphics.Color
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

// Basic Pen Colors
val MidnightBlack = ShopPen.Basic(Color(0xFF101820), 25, true, true) // Default, always unlocked
val CrimsonRed = ShopPen.Basic(Color(0xFFB22234))
val SunshineYellow = ShopPen.Basic(Color(0xFFF9D85D))
val OceanBlue = ShopPen.Basic(Color(0xFF1D4E89))
val EmeraldGreen = ShopPen.Basic(Color(0xFF4CAF50))
val FlameOrange = ShopPen.Basic(Color(0xFFF57F20))

// Premium Pen Colors
val RoseQuartz = ShopPen.Premium(Color(0xFFF4A6B8))
val RoyalPurple = ShopPen.Premium(Color(0xFF6A0FAB))
val TealDream = ShopPen.Premium(Color(0xFF008C92))
val GoldenGlow = ShopPen.Premium(Color(0xFFFFD700))
val CoralReef = ShopPen.Premium(Color(0xFFFF6F61))
val MajesticIndigo = ShopPen.Premium(Color(0xFF4B0082))
val CopperAura = ShopPen.Premium(Color(0xFFB87333))

// Legendary Pen Colors
val RainbowPenBrush = ShopPen.Legendary(
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