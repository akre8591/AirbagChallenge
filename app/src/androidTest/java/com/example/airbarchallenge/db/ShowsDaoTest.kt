package com.example.airbarchallenge.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.airbarchallenge.data.db.ShowEntity
import com.example.airbarchallenge.data.db.ShowsDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import javax.inject.Inject

@HiltAndroidTest
class ShowsDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Inject
    lateinit var showsDao: ShowsDao

    @Before
    fun setup() {
        hiltRule.inject()
        showsDao.deleteAllShows()
    }

    @Test
    fun saveShows() = runBlocking {
        assert(showsDao.getAllShows().isEmpty())

        val showList = listOf(
            ShowEntity(0, "name_test", 8.2, "fake_path", "fake_overview"),
            ShowEntity(1, "name_test", 9.2, "fake_path", "fake_overview")
        )

        showsDao.insertAllShows(showList)

        assert(showsDao.getAllShows().isNotEmpty())

    }

    @Test
    fun deleteAllShows() = runBlocking {
        assert(showsDao.getAllShows().isEmpty())

        val showList = listOf(
            ShowEntity(0, "name_test", 8.2, "fake_path", "fake_overview"),
            ShowEntity(1, "name_test", 9.2, "fake_path", "fake_overview")
        )

        showsDao.insertAllShows(showList)

        assert(showsDao.getAllShows().isNotEmpty())

        showsDao.deleteAllShows()

        assert(showsDao.getAllShows().isEmpty())

    }

    @Test
    fun getShowById() = runBlocking {
        assert(showsDao.getAllShows().isEmpty())

        val showList = listOf(
            ShowEntity(0, "name_test", 8.2, "fake_path", "fake_overview"),
            ShowEntity(1, "name_test", 9.2, "fake_path", "fake_overview")
        )

        showsDao.insertAllShows(showList)

        assertEquals(showsDao.getShowByID(0)?.id, 0)
        assertEquals(showsDao.getShowByID(0)?.name, "name_test")
        assertEquals(showsDao.getShowByID(0)?.voteAverage, 8.2)
        assertEquals(showsDao.getShowByID(0)?.posterPath, "fake_path")
        assertEquals(showsDao.getShowByID(0)?.overview, "fake_overview")
    }

    @After
    fun tearDown() {
        showsDao.deleteAllShows()
    }


}