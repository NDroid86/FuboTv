package com.nishant.fubotv.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nishant.fubotv.model.Event
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

/**
 * Created by Nishant Rajput on 16/09/22.
 *
 */
@Dao
interface EventsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(event: Event)

    @Query("Select * from event GROUP BY event_date")
    fun gelAllEvents(): Flow<MutableList<Event>>

    @Query("Select * from event WHERE event_date=:event_date")
    fun getEventByDate(event_date: LocalDate): Event

    @Query("Select * from event WHERE event_date=:event_date")
    fun getEventsByDate(event_date: LocalDate): MutableList<Event>
}