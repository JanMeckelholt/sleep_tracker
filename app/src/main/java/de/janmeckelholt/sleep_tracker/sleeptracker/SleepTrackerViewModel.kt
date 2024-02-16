package de.janmeckelholt.sleep_tracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import de.janmeckelholt.sleep_tracker.database.SleepDatabaseDao

class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
}