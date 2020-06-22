package com.jemmycalak.movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import com.jemmycalak.common.utils.Event
import com.jemmycalak.common_test.dataset.MovieDataset.FAKE_MOVIERESULT
import com.jemmycalak.common_test.dataset.MovieDataset.FAKE_MOVIES
import com.jemmycalak.common_test.extension.blockingObserve
import com.jemmycalak.model.MovieModel
import com.jemmycalak.movie.fragment.ListMovieFragmentDirections
import com.jemmycalak.movie.service.MovieService
import com.jemmycalak.movie.viewmodel.ListMovieViewModel
import com.jemmycalak.navigation.NavigationCommand
import com.jemmycalak.repository.utils.AppDispatchers
import com.jemmycalak.repository.utils.Resource
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class ListMovieFragmentTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MovieService
    private lateinit var listMovieViewModel: ListMovieViewModel
    private val appDispatchers = AppDispatchers(Dispatchers.Unconfined, Dispatchers.Unconfined)
    private lateinit var observer : Observer<Resource<MovieModel>>

    @Before
    fun setUp(){
        service = mockk()
        observer = mockk(relaxed=true)
    }

    @Test
    fun `User make request list movie`(){
        val fakeResult = Resource.success(FAKE_MOVIES)
        coEvery {
            service(any(), any(), any())
        } returns MutableLiveData<Resource<MovieModel>>().apply { value = fakeResult }

        listMovieViewModel = ListMovieViewModel(service, appDispatchers)
        listMovieViewModel.listMovieData.observeForever(observer)

        verify {
            observer.onChanged(fakeResult)
        }
        confirmVerified(observer)
    }

    @Test
    fun `on click to detail movie`(){
        val event = Event(NavigationCommand.To(
            ListMovieFragmentDirections.listMovieToDetailMovie(
                FAKE_MOVIERESULT.first()
            )
        ))

        coEvery { service(any(), any(), any())
        }returns MutableLiveData<Resource<MovieModel>>().apply { value = Resource.success(FAKE_MOVIES) }

        listMovieViewModel = ListMovieViewModel(service, appDispatchers)
        listMovieViewModel.onDetailMovie(FAKE_MOVIERESULT.first())

        Assert.assertEquals(event.peekContent(), listMovieViewModel.navigation.blockingObserve()?.peekContent())
    }

}