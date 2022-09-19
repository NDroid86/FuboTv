package com.nishant.fubotv

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.nishant.fubotv.data.AppDatabase
import com.nishant.fubotv.data.EventsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Created by Nishant Rajput on 15/09/22.
 *
 */
class FuboTv : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { EventsRepository(this) }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}