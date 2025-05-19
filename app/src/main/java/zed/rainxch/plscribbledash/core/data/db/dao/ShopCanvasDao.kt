package zed.rainxch.plscribbledash.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import zed.rainxch.plscribbledash.core.data.db.entity.ShopCanvasEntity
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas

@Dao
interface ShopCanvasDao {

    @Query("SELECT * FROM shop_canvas WHERE id = :id")
    fun getCanvasById(id: Int): Flow<ShopCanvasEntity>

    @Insert
    suspend fun insertAllCanvases(list: List<ShopCanvasEntity>)

    @Query("SELECT id FROM shop_canvas WHERE canvas = :canvas LIMIT 1")
    suspend fun getCanvasIdByCanvas(canvas: ShopCanvas): Int?

    @Update
    suspend fun updateCanvas(canvas: ShopCanvasEntity)

    @Query("SELECT * FROM shop_canvas WHERE equipped = 1 LIMIT 1")
    suspend fun getEquippedCanvas() : ShopCanvasEntity?

    @Query("SELECT * FROM shop_canvas WHERE canvas = :canvas LIMIT 1")
    suspend fun getCanvasEntityByCanvas(canvas: ShopCanvas) : ShopCanvasEntity?

    @Query("SELECT * FROM shop_canvas")
    fun getCanvasList(): Flow<List<ShopCanvasEntity>>

    @Query("SELECT COUNT(*) FROM shop_canvas")
    fun getCanvasCount(): Int

    @Query("DELETE from shop_canvas")
    suspend fun clearCanvasList() : Int
}