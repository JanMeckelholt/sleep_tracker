package de.janmeckelholt.sleep_tracker.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.janmeckelholt.sleep_tracker.database.SleepDatabaseDao
import java.lang.IllegalArgumentException

class SleepQualityViewModelFactory(private val dataSource : SleepDatabaseDao, private val sleepNightKey: Long) :
    ViewModelProvider.Factory {
    override fun <T: ViewModel>create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)){
            return SleepQualityViewModel(dataSource, sleepNightKey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}