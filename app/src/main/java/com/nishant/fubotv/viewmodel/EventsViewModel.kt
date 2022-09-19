package com.nishant.fubotv.viewmodel

import androidx.lifecycle.*
import com.nishant.fubotv.data.EventsRepository
import com.nishant.fubotv.model.Event
import kotlinx.coroutines.launch

/**
 * Created by Nishant Rajput on 16/09/22.
 *
 */
class EventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    val allEvents: LiveData<MutableList<Event>> = eventsRepository.allEvents.asLiveData()

    // Launching a new coroutine to insert the data in a non-blocking way
    fun insert(event: Event) = viewModelScope.launch {
        eventsRepository.insertEvent(event)
    }

    fun getEventByDate(event: Event): Event {
        return eventsRepository.getEventByDate(event)
    }

    fun getEventsByDate(event: Event): MutableList<Event> {
        return eventsRepository.getEventsByDate(event)
    }

    /*fun delete(event: Event) = viewModelScope.launch {
        eventsRepository.delete(event)
    }*/

    class EventViewModelFactory(private val eventsRepository: EventsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EventsViewModel(eventsRepository) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }
    }
}