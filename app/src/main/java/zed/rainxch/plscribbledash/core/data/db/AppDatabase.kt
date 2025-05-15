package zed.rainxch.plscribbledash.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import zed.rainxch.plscribbledash.core.data.db.dao.StatisticsDao
import zed.rainxch.plscribbledash.core.data.db.entity.StatisticEntity

@Database(
    entities = [StatisticEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun statisticsDao(): StatisticsDao
}