package zed.rainxch.plscribbledash.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import zed.rainxch.plscribbledash.core.data.db.converters.ShopCanvasTypeConverter
import zed.rainxch.plscribbledash.core.data.db.converters.ShopPenTypeConverter
import zed.rainxch.plscribbledash.core.data.db.dao.ShopCanvasDao
import zed.rainxch.plscribbledash.core.data.db.dao.ShopPenDao
import zed.rainxch.plscribbledash.core.data.db.dao.StatisticsDao
import zed.rainxch.plscribbledash.core.data.db.entity.ShopCanvasEntity
import zed.rainxch.plscribbledash.core.data.db.entity.ShopPenEntity
import zed.rainxch.plscribbledash.core.data.db.entity.StatisticEntity

@TypeConverters(ShopPenTypeConverter::class, ShopCanvasTypeConverter::class)
@Database(
    entities = [StatisticEntity::class, ShopPenEntity::class, ShopCanvasEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val statisticsDao: StatisticsDao
    abstract val shopPenDao: ShopPenDao
    abstract val shopCanvasDao : ShopCanvasDao
}