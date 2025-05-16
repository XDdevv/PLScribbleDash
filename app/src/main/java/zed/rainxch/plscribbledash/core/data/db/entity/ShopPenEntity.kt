package zed.rainxch.plscribbledash.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import zed.rainxch.plscribbledash.core.domain.model.SerializableShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

@Entity("shop_pen")
data class ShopPenEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pen: ShopPen
)