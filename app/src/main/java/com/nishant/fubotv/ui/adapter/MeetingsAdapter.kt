package com.nishant.fubotv.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nishant.fubotv.R
import com.nishant.fubotv.model.Event
import com.nishant.fubotv.utils.formatToViewTimeDefaults
import com.nishant.fubotv.viewmodel.EventsViewModel
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by Nishant Rajput on 17/09/22.
 *
 */
class MeetingsAdapter(private val mList: List<Event>, val eventsViewModel: EventsViewModel) :
    RecyclerView.Adapter<MeetingsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_meeting, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val meeting = mList[position]
        holder.textViewDate.text = meeting.event_date.format(DateTimeFormatter.ofPattern("MMM dd"))

        val listEvents = eventsViewModel.getEventsByDate(meeting)
        listEvents.forEach {
            val tv_meeting = TextView(holder.itemView.context)
            tv_meeting.setTextColor(Color.parseColor("#ffffff"))
            val title = it.event_name
            val event_time = String.format(
                "%s - %s",
                meeting.event_start_time.formatToViewTimeDefaults(),
                meeting.event_end_time.formatToViewTimeDefaults()
            )
            val diff: Long = it.event_end_time.getTime() - it.event_start_time.getTime()
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60

            val duration = if (hours > 1) String.format("(%s hour)", hours) else String.format(
                "(%s minutes)",
                minutes
            )
            val meeting_date_time = String.format("%s: %s %s", title, event_time, duration)
            tv_meeting.text = meeting_date_time

            val params: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            params.setMargins(10, 10, 10, 10)
            tv_meeting.setLayoutParams(params)

            holder.meeting_view.addView(tv_meeting)
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.textView_date)
        val meeting_view: LinearLayout = itemView.findViewById(R.id.meeting_view)
    }
}