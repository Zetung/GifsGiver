package com.zetung.gifsgiver.util

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.implementation.GifRoom
import com.zetung.gifsgiver.repository.model.DataGif
import com.zetung.gifsgiver.repository.model.DataObject
import com.zetung.gifsgiver.repository.model.Gif
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.di.GifsSingleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GifsGiverImplTest {

    @Mock private lateinit var gifsSingleton: GifsSingleton

    private lateinit var gifsGiverImpl: GifsGiverImpl

    private lateinit var internetList : MutableList<DataObject>
    private lateinit var dbList : MutableList<GifModel>
    private lateinit var resultList : MutableList<GifModel>

    @Before
    fun setUp() {
        internetList = mutableListOf(
            DataObject("testID1", DataGif(Gif(
                "https://media1.giphy.com/media/gqoaqRxpeo2KAQc3JX/giphy.gif?cid=a4fb3c12wcffg4ot8yrpry9ll9fprkeadj4ghusy2nxm5j9a&ep=v1_gifs_trending&rid=giphy.gif&ct=g"
                , false))),
            DataObject("testID2", DataGif(Gif(
                "https://media1.giphy.com/media/gqoaqRxpeo2KAQc3JX/giphy.gif?cid=a4fb3c12wcffg4ot8yrpry9ll9fprkeadj4ghusy2nxm5j9a&ep=v1_gifs_trending&rid=giphy.gif&ct=g"
                , false))),
            DataObject("testID3", DataGif(Gif(
                "https://media1.giphy.com/media/gqoaqRxpeo2KAQc3JX/giphy.gif?cid=a4fb3c12wcffg4ot8yrpry9ll9fprkeadj4ghusy2nxm5j9a&ep=v1_gifs_trending&rid=giphy.gif&ct=g"
                , false))),
        )

        dbList = mutableListOf(
            GifModel("testID2",
                "https://media1.giphy.com/media/gqoaqRxpeo2KAQc3JX/giphy.gif?cid=a4fb3c12wcffg4ot8yrpry9ll9fprkeadj4ghusy2nxm5j9a&ep=v1_gifs_trending&rid=giphy.gif&ct=g",
                true),
            GifModel("testID3",
                "https://media1.giphy.com/media/gqoaqRxpeo2KAQc3JX/giphy.gif?cid=a4fb3c12wcffg4ot8yrpry9ll9fprkeadj4ghusy2nxm5j9a&ep=v1_gifs_trending&rid=giphy.gif&ct=g",
                true)
        )

        resultList = mutableListOf(
            GifModel("testID1",
                "https://media1.giphy.com/media/gqoaqRxpeo2KAQc3JX/giphy.gif?cid=a4fb3c12wcffg4ot8yrpry9ll9fprkeadj4ghusy2nxm5j9a&ep=v1_gifs_trending&rid=giphy.gif&ct=g",
                false),
            GifModel("testID2",
                "https://media1.giphy.com/media/gqoaqRxpeo2KAQc3JX/giphy.gif?cid=a4fb3c12wcffg4ot8yrpry9ll9fprkeadj4ghusy2nxm5j9a&ep=v1_gifs_trending&rid=giphy.gif&ct=g",
                true),
            GifModel("testID3",
                "https://media1.giphy.com/media/gqoaqRxpeo2KAQc3JX/giphy.gif?cid=a4fb3c12wcffg4ot8yrpry9ll9fprkeadj4ghusy2nxm5j9a&ep=v1_gifs_trending&rid=giphy.gif&ct=g",
                true)
        )

        gifsSingleton = GifsSingleton()
    }


    @Test
    fun `load gifs with internet and db`() = runTest{

        val connectionApiMock = Mockito.mock(ConnectionApi::class.java)
        Mockito.`when`(connectionApiMock.loadGif()).thenReturn(internetList)

        val gifDbApiMock = Mockito.mock(GifDbApi::class.java)
        Mockito.`when`(gifDbApiMock.getAllFavorites()).thenReturn(dbList)

        gifsGiverImpl = GifsGiverImpl(connectionApiMock,gifDbApiMock,gifsSingleton)

        val result = gifsGiverImpl.loadGifs().last()
        assertTrue(result.containsAll(resultList))
    }


    @Test
    fun `check state on load gifs with internet and db`() = runTest{
        val connectionApiMock = Mockito.mock(ConnectionApi::class.java)
        Mockito.`when`(connectionApiMock.loadGif()).thenReturn(internetList)
        Mockito.`when`(connectionApiMock.getState()).thenReturn(LoadState.Done())

        val gifDbApiMock = Mockito.mock(GifDbApi::class.java)
        Mockito.`when`(gifDbApiMock.getAllFavorites()).thenReturn(dbList)

        gifsGiverImpl = GifsGiverImpl(connectionApiMock,gifDbApiMock,gifsSingleton)
        var state = gifsGiverImpl.getState()
        assertTrue(state is LoadState.NotStarted)

        val result = gifsGiverImpl.loadGifs()
        result.collect{
            state = gifsGiverImpl.getState()
            assertTrue(state is LoadState.Done)
        }
    }

    @Test
    fun `check state on load gifs with no internet and db`() = runTest{
        val connectionApiMock = Mockito.mock(ConnectionApi::class.java)
        Mockito.`when`(connectionApiMock.loadGif()).thenReturn(mutableListOf())
        Mockito.`when`(connectionApiMock.getState()).thenReturn(LoadState.Error())

        val gifDbApiMock = Mockito.mock(GifDbApi::class.java)
        Mockito.`when`(gifDbApiMock.getAllFavorites()).thenReturn(dbList)

        gifsGiverImpl = GifsGiverImpl(connectionApiMock,gifDbApiMock,gifsSingleton)
        var state = gifsGiverImpl.getState()
        assertTrue(state is LoadState.NotStarted)

        val result = gifsGiverImpl.loadGifs()
        result.collect{
            state = gifsGiverImpl.getState()
            assertTrue(state is LoadState.Error)
        }
    }


    @Test
    fun `check state on load gifs with internet and no db`() = runTest{
        val connectionApiMock = Mockito.mock(ConnectionApi::class.java)
        Mockito.`when`(connectionApiMock.loadGif()).thenReturn(internetList)
        Mockito.`when`(connectionApiMock.getState()).thenReturn(LoadState.Done())

        val gifDbApiMock = Mockito.mock(GifDbApi::class.java)
        Mockito.`when`(gifDbApiMock.getAllFavorites()).thenReturn(mutableListOf())
        Mockito.`when`(gifDbApiMock.getState()).thenReturn(LoadState.Error())

        gifsGiverImpl = GifsGiverImpl(connectionApiMock,gifDbApiMock,gifsSingleton)
        var state = gifsGiverImpl.getState()
        assertTrue(state is LoadState.NotStarted)

        val result = gifsGiverImpl.loadGifs()
        result.collect{
            state = gifsGiverImpl.getState()
            assertTrue(state is LoadState.Error)
        }
    }


    @Test
    fun `check state on insert to favorites`() = runTest{
        val connectionApiMock = Mockito.mock(ConnectionApi::class.java)

        val gifDbApiMock = Mockito.mock(GifDbApi::class.java)
        Mockito.`when`(gifDbApiMock.getState()).thenReturn(LoadState.Done())

        gifsGiverImpl = GifsGiverImpl(connectionApiMock,gifDbApiMock,gifsSingleton)

        gifsGiverImpl.addToFavorite("id","url")
        val state = gifsGiverImpl.getState()
        assertTrue(state is LoadState.Done)
    }


    @Test
    fun `check state on error insert to favorites`() = runTest{
        val connectionApiMock = Mockito.mock(ConnectionApi::class.java)

        val gifDbApiMock = Mockito.mock(GifDbApi::class.java)
        Mockito.`when`(gifDbApiMock.getState()).thenReturn(LoadState.Error())

        gifsGiverImpl = GifsGiverImpl(connectionApiMock,gifDbApiMock,gifsSingleton)

        gifsGiverImpl.addToFavorite("id","url")
        val state = gifsGiverImpl.getState()
        assertTrue(state is LoadState.Error)
    }

    @Test
    fun `check state on delete from favorites`() = runTest{
        val connectionApiMock = Mockito.mock(ConnectionApi::class.java)

        val gifDbApiMock = Mockito.mock(GifDbApi::class.java)
        Mockito.`when`(gifDbApiMock.getState()).thenReturn(LoadState.Done())

        gifsGiverImpl = GifsGiverImpl(connectionApiMock,gifDbApiMock,gifsSingleton)

        gifsGiverImpl.deleteFromFavorite("id")
        val state = gifsGiverImpl.getState()
        assertTrue(state is LoadState.Done)
    }

    @Test
    fun `check state on error delete from favorites`() = runTest{
        val connectionApiMock = Mockito.mock(ConnectionApi::class.java)

        val gifDbApiMock = Mockito.mock(GifDbApi::class.java)
        Mockito.`when`(gifDbApiMock.getState()).thenReturn(LoadState.Error())

        gifsGiverImpl = GifsGiverImpl(connectionApiMock,gifDbApiMock,gifsSingleton)

        gifsGiverImpl.deleteFromFavorite("id")
        val state = gifsGiverImpl.getState()
        assertTrue(state is LoadState.Error)
    }
}