package zed.rainxch.plscribbledash.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import zed.rainxch.plscribbledash.core.data.db.entity.ShopPenEntity

@Dao
interface ShopPenDao {

    @Query("SELECT * FROM shop_pen WHERE id = :id")
    fun getPenById(id: Int): ShopPenEntity

    @Insert
    fun insertAllPens(list: List<ShopPenEntity>)

    @Update
    fun updatePen(pen: ShopPenEntity)
}