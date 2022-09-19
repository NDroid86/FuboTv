package com.nishant.fubotv.data

import android.content.Context
import androidx.annotation.WorkerThread
import com.nishant.fubotv.model.Event
import kotlinx.coroutines.flow.Flow

/**
 * Created by Nishant Rajput on 16/09/22.
 *
 */
class EventsRepository(context: Context) {

    var db: EventsDAO = AppDatabase.getInstance(context)?.eventDAO()!!

    //Fetch All the Events
    val allEvents: Flow<MutableList<Event>>  = db.gelAllEvents()

    // Insert new event
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertEvent(event: Event) {
        db.insertEvent(event)
    }

    fun getEventByDate(event: Event) : Event {
        return db.getEventByDate(event.event_date)
    }

    fun getEventsByDate(event: Event) : MutableList<Event> {
        return db.getEventsByDate(event.event_date)
    }
}