package zed.rainxch.plscribbledash.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import zed.rainxch.plscribbledash.core.domain.model.SerializableShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas

@Entity("shop_canvas")
data class ShopCanvasEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val canvas: ShopCanvas
)