package zed.rainxch.plscribbledash.core.data.datasource.static_

import androidx.compose.ui.graphics.Color
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas

val shopCanvasList = listOf(
    ShopCanvas.Basic(Color(0xFFFFFFFF), canvasBought = true, canvasEquipped = true, canvasName = "basic First"),
    ShopCanvas.Basic(Color(0xFFE0E0E0), canvasBought = true, canvasName = "basic second"),
    ShopCanvas.Basic(Color(0xFFF5F5DC), canvasName = "basic third"),
    ShopCanvas.Basic(Color(0xFFB0C4DE), canvasName = "basic fourth"),
    ShopCanvas.Basic(Color(0xFFD3E8D2), canvasName = "basic fifth"),
    ShopCanvas.Basic(Color(0xFFF4E1D9), canvasName = "basic sixth"),
    ShopCanvas.Basic(Color(0xFFE7D8E9), canvasName = "basic seventh"),
    ShopCanvas.Premium(Color(0xFFB8CBB8), canvasName = "premium First"),
    ShopCanvas.Premium(Color(0xFFD1B2C1), canvasName = "premium second"),
    ShopCanvas.Premium(Color(0xFFA3BFD9), canvasName = "premium third"),
    ShopCanvas.Premium(Color(0xFFD8D6C1), canvasName = "premium fourth"),
    ShopCanvas.Premium(Color(0xFFF2C5C3), canvasName = "premium fifth"),
    ShopCanvas.Premium(Color(0xFFD9EDE1), canvasName = "premium sixth"),
    ShopCanvas.Premium(Color(0xFFE2D3E8), canvasName = "premium seventh"),
    ShopCanvas.Legendary(R.drawable.bg_wood_texture, canvasName = "Legendary first"),
    ShopCanvas.Legendary(R.drawable.bg_vintage_notebook, canvasName = "Legendary second"),
)