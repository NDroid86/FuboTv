package com.nishant.fubotv.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishant.fubotv.FuboTv
import com.nishant.fubotv.databinding.ActivityMainBinding
import com.nishant.fubotv.ui.adapter.MeetingsAdapter
import com.nishant.fubotv.viewmodel.EventsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val eventsViewModel: EventsViewModel by viewModels {
        EventsViewModel.EventViewModelFactory((application as FuboTv).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater())
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            val intent = Intent(this, CreateEventActivity::class.java)
            startActivity(intent)
        }

        eventsViewModel.allEvents.observe(this,{ events ->
            events?.let {
                if (it.size > 0){
                    binding.emptyView.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                    val adapter = MeetingsAdapter(it, eventsViewModel)
                    binding.recyclerview.adapter = adapter
                } else {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.recyclerview.visibility = View.GONE
                }
            }

            events.forEach {
                Log.d("Events", it.toString())
            }
        })
    }
}