package de.janmeckelholt.sleep_tracker.sleeptracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import de.janmeckelholt.sleep_tracker.database.SleepDatabaseDao
import de.janmeckelholt.sleep_tracker.database.SleepNight
import de.janmeckelholt.sleep_tracker.formatNights
import kotlinx.coroutines.launch
import timber.log.Timber

class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application) :
    AndroidViewModel(application) {
    private var tonight = MutableLiveData<SleepNight?>()
    private val nights = database.getAllNights()
    val nightsStr = nights.map {
        formatNights(it, application.resources)
    }

    val startButtonVisible = tonight.map {
        return@map (it == null)
    }

    val stopButtonVisible = tonight.map {
        return@map (it != null)
    }

    private var _showSnackBarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent : LiveData<Boolean>
        get() = _showSnackBarEvent

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value=false
    }


    private  val _navigateToSleepQuality = MutableLiveData<SleepNight?>()
    val navigateToSleepQuality : LiveData<SleepNight?>
        get() = _navigateToSleepQuality

    fun doneNavigating(){
        _navigateToSleepQuality.value=null
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
                _navigateToSleepQuality.value=it
            }
        }

    }

    fun onClear() {
        Timber.e("----------->onClear")
        viewModelScope.launch {
            clear()
            tonight.value = null
            _showSnackBarEvent.value = true
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