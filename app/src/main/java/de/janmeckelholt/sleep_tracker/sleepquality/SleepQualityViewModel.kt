package de.janmeckelholt.sleep_tracker.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.janmeckelholt.sleep_tracker.database.SleepDatabaseDao
import de.janmeckelholt.sleep_tracker.database.SleepNight
import kotlinx.coroutines.launch
import timber.log.Timber

class SleepQualityViewModel(val database: SleepDatabaseDao, private val sleepNightKey: Long = 0L) :
    ViewModel() {
    private var night = MutableLiveData<SleepNight>()
    private val _navigateToSleepTracker = MutableLiveData<Boolean>()

    val navigateToSleepTracker: LiveData<Boolean>
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        Timber.i("done navigating in SQuality")
        _navigateToSleepTracker.value = false
    }

    fun onQualitySelected(quality: Int) {
        Timber.i("selected Q $quality")
        viewModelScope.launch {
            val nightToBeUpdated = getNightFromDatabase(sleepNightKey)
            nightToBeUpdated?.let {
                it.sleepQuality = quality
                updateNightInDatabase(it)
                night.value = it
            _navigateToSleepTracker.value=true
            }
        }
    }

    suspend fun getNightFromDatabase(sleepId: Long): SleepNight? {
        return database.get(key = sleepId)
    }

    suspend fun updateNightInDatabase(sleepNight: SleepNight) {
        database.update(sleepNight)
    }

}