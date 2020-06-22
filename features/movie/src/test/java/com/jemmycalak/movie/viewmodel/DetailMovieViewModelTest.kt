package com.jemmycalak.movie.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import com.jemmycalak.common_test.dataset.MovieDataset.FAKE_REVIEW
import com.jemmycalak.common_test.extension.getValue
import com.jemmycalak.local.AppDatabase
import com.jemmycalak.model.Result
import com.jemmycalak.model.ResultReview
import com.jemmycalak.model.ReviewMovie
import com.jemmycalak.movie.service.MovieService
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

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class DetailMovieViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel : DetailMovieViewModel
    private lateinit var service : MovieService
    private val appDispatchers = AppDispatchers(Dispatchers.Unconfined, Dispatchers.Unconfined)
    private lateinit var observer: Observer<Resource<ReviewMovie>>

    @Before
    fun setUp() {
        service = mockk(relaxed = true)
        observer = mockk(relaxed = true)

        viewModel = DetailMovieViewModel(service, appDispatchers)
    }

    @Test
    @Throws(InterruptedException::class)
    fun `show detail movie and request review movie`(){
        val data = service.getResult(any())

        val fakeReview = Resource.success(FAKE_REVIEW)
        coEvery {
            service.getReviewMovie(any())
        }returns MutableLiveData<Resource<ReviewMovie>>().apply { value =fakeReview }

        viewModel.listReviewMovieData.observeForever(observer)
        viewModel.getModel(any())

        verify {
            observer.onChanged(fakeReview)
        }

        confirmVerified(observer)
        Assert.assertEquals(data, viewModel.model)
    }
}