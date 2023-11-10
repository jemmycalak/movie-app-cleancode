package com.jemmycalak.repository

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.jemmycalak.local.dao.MovieDao
import com.jemmycalak.local.db.MovieTable
import com.jemmycalak.model.MovieModel
import com.jemmycalak.model.Result
import com.jemmycalak.model.ReviewMovie
import com.jemmycalak.remote.source.MovieDataSource
import com.jemmycalak.repository.utils.NetworkBoundResource
import com.jemmycalak.repository.utils.Resource
import retrofit2.Response
import java.util.Calendar

interface MovieRepositoryInterface {
    suspend fun getListMovie(
        filter: Int,
        page: Int,
        shouldFetch: Boolean
    ): LiveData<Resource<MovieModel>>

    suspend fun getReviewMovie(idMovie: String): LiveData<Resource<ReviewMovie>>


    fun getResult(id: Int?): LiveData<Result>
    fun updateFavorite(result: Boolean?, id: Int?)
    fun getListFavorite(): LiveData<List<Result>>
}

class MovieRepository(
    private val source: MovieDataSource,
    private val dao: MovieDao,
    private val gson: Gson
) : MovieRepositoryInterface {
    override suspend fun getListMovie(
        filter: Int,
        page: Int,
        shouldFetch: Boolean
    ): LiveData<Resource<MovieModel>> {
        return object : NetworkBoundResource<MovieModel, MovieModel>(gson) {
            override fun processResponse(response: MovieModel): MovieModel = response

            override suspend fun saveCallResults(data: MovieModel) {
                val dateNow = Calendar.getInstance().timeInMillis
                val model = dao.getMovie()

                model?.let {
                    val dateDate: Calendar = Calendar.getInstance()
                    dateDate.timeInMillis = it.date!!.toLong()

                    val nextDate = Calendar.getInstance()
                    nextDate.timeInMillis = it.date!!.toLong()
                    nextDate.add(Calendar.DATE, 1)

                    /*
                     * if data > a day
                     * data will be remove
                     * */
                    if (dateDate.after(nextDate)) {
                        dao.removeMovieTable()
                        dao.removeMovieResult()
                    }
                }

                dao.saveMovieModel(
                    MovieTable(
                        data.page,
                        data.totalPages,
                        data.totalResults,
                        dateNow.toString()
                    )
                )
                data.results?.forEach {
                    it.filter = filter
                    dao.saveMovieList(it)
                }
            }

            override fun shouldFetch(data: MovieModel?): Boolean = shouldFetch

            override suspend fun loadFromDb(): MovieModel? {
                val model = dao.getMovie()
                val result = dao.getResultMovie(filter)
                return if (model != null) MovieModel(
                    model.page,
                    result,
                    model.totalPages,
                    model.totalResults
                ) else null
            }

            override suspend fun createCallAsync(): Response<MovieModel> =
                source.getListMovie(filter, page)

        }.build().asLiveData()
    }

    override suspend fun getReviewMovie(idMovie: String): LiveData<Resource<ReviewMovie>> {
        return object : NetworkBoundResource<ReviewMovie, ReviewMovie>(gson) {
            override fun processResponse(response: ReviewMovie): ReviewMovie = response

            override suspend fun saveCallResults(data: ReviewMovie) {}

            override fun shouldFetch(data: ReviewMovie?): Boolean = true

            override suspend fun loadFromDb(): ReviewMovie? = null

            override suspend fun createCallAsync(): Response<ReviewMovie> =
                source.getListReviewMovie(idMovie)
        }.build().asLiveData()
    }

    override fun getResult(id: Int?): LiveData<Result> = dao.getResult(id)

    override fun updateFavorite(result: Boolean?, id: Int?) = dao.updateFavorite(result, id)

    override fun getListFavorite(): LiveData<List<Result>> = dao.getListFavorite(true)
}