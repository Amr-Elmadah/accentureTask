package com.tasks.accenturetask.ui.albums.presetation.view.activity

import android.content.Context
import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.tasks.accenturetask.R
import com.tasks.accenturetask.data.local.dao.AlbumDao
import com.tasks.accenturetask.data.local.database.AlbumsDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class AlbumsActivityTest {
    @get:Rule
    val rule = ActivityRule(AlbumsActivity::class.java)

    private lateinit var userDao: AlbumDao

    private lateinit var db: AlbumsDatabase

    @Before
    fun createDb() {
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(
            context, AlbumsDatabase::class.java
        ).build()
        userDao = db.AlbumDao()
    }

    @Test
    fun testToolbarDisplayed() {
        onView(withId(R.id.toolbar)).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun testAlbumsRecyclerViewDisplayed() {
        onView(withId(R.id.rvAlbums)).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun testToolbarTextDisplayed() {
        Thread.sleep(3000)
        onView(withText(R.string.album_list)).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun checkCachedAlbums() {
        val response = userDao.getAllAlbums().value
        assert(response.isNullOrEmpty())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}