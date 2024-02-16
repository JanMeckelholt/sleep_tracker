package de.janmeckelholt.sleep_tracker

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.janmeckelholt.sleep_tracker.database.SleepDatabase
import de.janmeckelholt.sleep_tracker.database.SleepDatabaseDao
import de.janmeckelholt.sleep_tracker.database.SleepNight
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        sleepDao = db.sleepDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndGetNight() {
        val night = SleepNight()
        runBlocking {
            sleepDao.insert(night)
            val tonight = sleepDao.getTonight()
            assertEquals(-1, tonight?.sleepQuality)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testGetAll() {
        runBlocking {
            val night1 = SleepNight()
            val night2 = SleepNight(startTimeMilli = 1111, endTimeMilli = 2222, sleepQuality = 4)
            sleepDao.insert(night1)
            sleepDao.insert(night2)
            val nights = sleepDao.getAllNights()
            assertEquals(2, nights.size )
        }
    }

}

