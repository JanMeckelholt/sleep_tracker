package de.janmeckelholt.sleep_tracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import de.janmeckelholt.sleep_tracker.database.SleepDatabaseDao
import de.janmeckelholt.sleep_tracker.database.SleepNight
import de.janmeckelholt.sleep_tracker.formatNights
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application) :
    AndroidViewModel(application) {
    private var tonight = MutableLiveData<SleepNight?>()
    private val nights = database.getAllNights()
    val nightsStr = nights.map {
        formatNights(it, application.resources)
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = database.getTonight()
        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }

    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            tonight.value?.let {
                it.endTimeMilli = System.currentTimeMillis()
                update(it)
            }
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            tonight.value = null
        }
    }


    private suspend fun insert(night: SleepNight) {
        database.insert(sleepNight = night)
    }

    private suspend fun update(night: SleepNight) {
        database.update(sleepNight = night)
    }

    private suspend fun clear() {
        database.clear()
    }

}