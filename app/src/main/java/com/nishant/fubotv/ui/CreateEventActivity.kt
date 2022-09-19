package com.nishant.fubotv.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nishant.fubotv.FuboTv
import com.nishant.fubotv.databinding.ActivityCreateEventBinding
import com.nishant.fubotv.model.Event
import com.nishant.fubotv.utils.formatToViewDateTimeDefaults
import com.nishant.fubotv.utils.snackbar
import com.nishant.fubotv.viewmodel.EventsViewModel
import org.threeten.bp.LocalDate
import java.util.*

class CreateEventActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityCreateEventBinding
    private var isStart: Boolean = false
    private var start_time: Date = Calendar.getInstance().time
    private var end_time: Date = Calendar.getInstance().time
    private lateinit var selected_date: LocalDate
    private val eventsViewModel: EventsViewModel by viewModels {
        EventsViewModel.EventViewModelFactory((application as FuboTv).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etEventStartTime.inputType = InputType.TYPE_NULL
        binding.etEventStartTime.setOnClickListener {
            isStart = true
            pickDateTime()
        }

        binding.etEventEndTime.inputType = InputType.TYPE_NULL
        binding.etEventEndTime.setOnClickListener {
            isStart = false
            pickDateTime()
        }

        binding.btnCreate.setOnClickListener {
            val meeting_name = binding.etMeetingName.text
            if (meeting_name.isNullOrEmpty()) {
                binding.etMeetingName.snackbar("Meeting name should not be empty.")
                return@setOnClickListener
            }

            //Check start time not after the end time
            if (start_time.after(end_time)) {
                binding.etEventStartTime.snackbar("Start time should be after end time.")
                return@setOnClickListener
            }


            val event = Event(0, meeting_name.toString(), selected_date, start_time, end_time)
            val existingEvent = eventsViewModel.getEventByDate(event)
            if (existingEvent != null) {
                if (event.event_start_time.after(existingEvent.event_end_time) || event.event_start_time.before(
                        existingEvent.event_start_time
                    )
                    && event.event_end_time.after(existingEvent.event_end_time) || event.event_end_time.before(
                        existingEvent.event_start_time
                    )
                ) {
                    eventsViewModel.insert(event)
                } else {
                    binding.etEventStartTime.snackbar("Exiting Meeting times will overlap, Please choose different time.")
                    return@setOnClickListener
                }
            } else {
                eventsViewModel.insert(event)
            }

            finish()
        }
    }

    private fun pickDateTime() {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this@CreateEventActivity, this@CreateEventActivity,
            startYear,
            startMonth,
            startDay
        )
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
        datePickerDialog.show()
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {

        selected_date = LocalDate.of(year, month + 1, day)
        Log.d("Date: ", selected_date.toString())

        val currentDateTime = Calendar.getInstance()
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        TimePickerDialog(
            this@CreateEventActivity,
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                if (isStart) {
                    binding.etEventStartTime.setText(
                        pickedDateTime.time.formatToViewDateTimeDefaults(
                            TimeZone.getDefault()
                        )
                    )
                    start_time = pickedDateTime.time
                } else {
                    binding.etEventEndTime.setText(
                        pickedDateTime.time.formatToViewDateTimeDefaults(
                            TimeZone.getDefault()
                        )
                    )
                    end_time = pickedDateTime.time
                }
            },
            startHour,
            startMinute,
            false
        ).show()
    }
}