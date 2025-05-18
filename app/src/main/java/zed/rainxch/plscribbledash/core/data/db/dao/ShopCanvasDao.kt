package zed.rainxch.plscribbledash.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import zed.rainxch.plscribbledash.core.data.db.entity.ShopCanvasEntity

@Dao
interface ShopCanvasDao {

    @Query("SELECT * FROM shop_canvas WHERE id = :id")
    fun getCanvasById(id: Int): Flow<ShopCanvasEntity?>

    @Insert
    suspend fun insertAllCanvases(list: List<ShopCanvasEntity>)

    @Update
    fun updateCanvas(canvas: ShopCanvasEntity)

    @Query("SELECT * FROM shop_canvas")
    fun getCanvasList(): Flow<List<ShopCanvasEntity>>

    @Query("SELECT COUNT(*) FROM shop_canvas")
    fun getCanvasCount(): Int

    @Query("DELETE from shop_canvas")
    suspend fun clearCanvasList() : Int
}