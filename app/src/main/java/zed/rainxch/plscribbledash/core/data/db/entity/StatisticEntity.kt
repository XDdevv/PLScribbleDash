package zed.rainxch.plscribbledash.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("statistics")
data class StatisticEntity(
    @PrimaryKey(autoGenerate = false) val id: String = "",
    val quantity: Int = 0
)
