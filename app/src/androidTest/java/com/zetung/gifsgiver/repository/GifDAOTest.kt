package com.zetung.gifsgiver.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zetung.gifsgiver.repository.model.GifModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GifDAOTest{

    private lateinit var context: Context
    private lateinit var gifDao: GifDAO

    private lateinit var database: LocalDb

    @Before
    fun setUp(){
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(
            context,
            LocalDb::class.java
        ).allowMainThreadQueries().build()
        gifDao = database.getFavoritesDAO()

    }

    @Test
    fun insertToDatabase() = runBlocking {
        val gifModel = GifModel("testID1234",
            "https://media1.giphy.com/media/gqoaqRxpeo2KAQc3JX/giphy.gif?cid=a4fb3c12wcffg4ot8yrpry9ll9fprkeadj4ghusy2nxm5j9a&ep=v1_gifs_trending&rid=giphy.gif&ct=g",
            true)

        gifDao.addToFavorite(gifModel)

        val allGifs = gifDao.getFavorites()

        assertTrue(allGifs.contains(gifModel))
    }

    @After
    fun tearDown(){
        database.close()
    }

}