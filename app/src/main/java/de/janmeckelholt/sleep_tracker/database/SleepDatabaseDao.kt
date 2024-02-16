package de.janmeckelholt.sleep_tracker.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate

@Dao
interface SleepDatabaseDao {
    @Insert
    suspend fun insert(sleepNight: SleepNight)

    @Update
    suspend fun update(sleepNight: SleepNight)

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :key")
    suspend fun get(key: Long) : SleepNight?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY start_time_milli DESC")
    fun getAllNights() : List<SleepNight>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend fun getTonight() : SleepNight?


    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

}