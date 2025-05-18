package zed.rainxch.plscribbledash.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import zed.rainxch.plscribbledash.core.data.db.entity.ShopPenEntity

@Dao
interface ShopPenDao {

    @Query("SELECT * FROM shop_pen WHERE id = :id")
    fun getPenById(id: Int): ShopPenEntity

    @Insert
    suspend fun insertAllPens(list: List<ShopPenEntity>)

    @Update
    fun updatePen(pen: ShopPenEntity)

    @Query("SELECT * FROM shop_pen")
    fun getPenList(): Flow<List<ShopPenEntity>>

    @Query("SELECT COUNT(*) FROM shop_pen")
    fun getPenCount(): Int

    @Query("DELETE from shop_pen")
    suspend fun clearPenList() : Int
}