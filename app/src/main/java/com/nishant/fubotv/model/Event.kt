package com.nishant.fubotv.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate
import java.util.*

/**
 * Created by Nishant Rajput on 15/09/22.
 *
 */
@Entity(tableName = "event")
data class Event(
    @PrimaryKey(autoGenerate = true) val event_id: Int = 0,
    @ColumnInfo(name = "event_name") val event_name: String,
    @ColumnInfo(name = "event_date") val event_date: LocalDate,
    @ColumnInfo(name = "event_start_time") val event_start_time: Date,
    @ColumnInfo(name = "event_end_time") val event_end_time: Date
)
