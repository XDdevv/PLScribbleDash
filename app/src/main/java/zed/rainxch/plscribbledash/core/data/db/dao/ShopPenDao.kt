package zed.rainxch.plscribbledash.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import zed.rainxch.plscribbledash.core.data.db.entity.ShopCanvasEntity
import zed.rainxch.plscribbledash.core.data.db.entity.ShopPenEntity
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.domain.model.ShopPen

@Dao
interface ShopPenDao {

    @Query("SELECT * FROM shop_pen WHERE id = :id")
    fun getPenById(id: Int): Flow<ShopPenEntity>

    @Insert
    suspend fun insertAllPens(list: List<ShopPenEntity>)

    @Update
    suspend fun updatePen(pen: ShopPenEntity)

    @Query("SELECT id FROM shop_pen WHERE pen = :pen LIMIT 1")
    suspend fun getPenIdByType(pen: ShopPen): Int?

    @Query("SELECT * FROM shop_pen WHERE equipped = 1 LIMIT 1")
    suspend fun getEquippedPen() : ShopPenEntity

    @Query("SELECT * FROM shop_pen")
    fun getPenList(): Flow<List<ShopPenEntity>>

    @Query("SELECT * FROM shop_pen WHERE id = :id LIMIT 1")
    suspend fun getPenEntityById(id: Int): ShopPenEntity

    @Query("SELECT COUNT(*) FROM shop_pen")
    fun getPenCount(): Int

    @Query("DELETE from shop_pen")
    suspend fun clearPenList() : Int

    @Query("SELECT * FROM shop_pen WHERE pen = :pen")
    suspend fun getPenEntityByPen(pen : ShopPen) : ShopPenEntity
}