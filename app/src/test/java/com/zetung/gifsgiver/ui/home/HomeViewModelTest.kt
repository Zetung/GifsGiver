package com.zetung.gifsgiver.ui.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zetung.gifsgiver.repository.model.Gif
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.ConnectionApi
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.GifsGiverImpl
import com.zetung.gifsgiver.util.LoadState
import com.zetung.gifsgiver.util.di.GifsSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest{

    @Mock
    private lateinit var gifsSingleton: GifsSingleton
    @Mock
    private lateinit var gifModel: GifModel
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp(){
        gifsSingleton = GifsSingleton()
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `set like`() = runBlocking{
        gifModel = GifModel("id","url",true)

        val gifsGiverApiMock = Mockito.mock(GifsGiverApi::class.java)
        Mockito.`when`(gifsGiverApiMock.addToFavorite(gifModel.id, gifModel.url)).thenReturn(Unit)
        Mockito.`when`(gifsGiverApiMock.getState()).thenReturn(LoadState.Done())

        homeViewModel = HomeViewModel(gifsGiverApiMock,gifsSingleton)
        homeViewModel.gifs.value = mutableListOf()
        val t = homeViewModel.setLike(gifModel)
        assertTrue(homeViewModel.gifs.value!!.contains(gifModel))
    }

    @Test
    fun `delete like`() {
        val gifsGiverApiMock = Mockito.mock(GifsGiverApi::class.java)
        Mockito.`when`(gifsGiverApiMock.getState()).thenReturn(LoadState.Done())

        val gifModel = GifModel("id","url",true)

        homeViewModel = HomeViewModel(gifsGiverApiMock,gifsSingleton)
        homeViewModel.deleteLike(gifModel)

        assertFalse(homeViewModel.gifs.value!!.contains(gifModel))
    }

}