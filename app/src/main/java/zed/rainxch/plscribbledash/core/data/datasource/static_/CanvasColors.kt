package zed.rainxch.plscribbledash.core.data.datasource.static_

import androidx.compose.ui.graphics.Color
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas

val shopCanvasList = listOf(
    ShopCanvas.Basic(Color(0xFFFFFFFF), canvasBought = true, canvasEquipped = true),
    ShopCanvas.Basic(Color(0xFFE0E0E0), canvasBought = true),
    ShopCanvas.Basic(Color(0xFFF5F5DC)),
    ShopCanvas.Basic(Color(0xFFB0C4DE)),
    ShopCanvas.Basic(Color(0xFFD3E8D2)),
    ShopCanvas.Basic(Color(0xFFF4E1D9)),
    ShopCanvas.Basic(Color(0xFFE7D8E9)),
    ShopCanvas.Premium(Color(0xFFB8CBB8)),
    ShopCanvas.Premium(Color(0xFFD1B2C1)),
    ShopCanvas.Premium(Color(0xFFA3BFD9)),
    ShopCanvas.Premium(Color(0xFFD8D6C1)),
    ShopCanvas.Premium(Color(0xFFF2C5C3)),
    ShopCanvas.Premium(Color(0xFFD9EDE1)),
    ShopCanvas.Premium(Color(0xFFE2D3E8)),
    ShopCanvas.Legendary(R.drawable.bg_wood_texture),
    ShopCanvas.Legendary(R.drawable.bg_vintage_notebook),
)