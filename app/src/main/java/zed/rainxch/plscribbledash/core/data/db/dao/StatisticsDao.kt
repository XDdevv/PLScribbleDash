package zed.rainxch.plscribbledash.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import zed.rainxch.plscribbledash.core.data.db.entity.StatisticEntity

@Dao
interface StatisticsDao {
    @Query("SELECT * FROM statistics")
    suspend fun getStatistics(): List<StatisticEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStatistic(statisticEntity: StatisticEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatistics(list: List<StatisticEntity>)

    @Query("SELECT COUNT(*) FROM statistics")
    suspend fun getStatisticsCount(): Int

    @Query("SELECT * FROM statistics WHERE id=:id")
    suspend fun getStatisticById(id: String): StatisticEntity
}