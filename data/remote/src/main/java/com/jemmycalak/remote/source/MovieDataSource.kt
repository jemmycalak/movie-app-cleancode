package com.jemmycalak.remote.source

import com.jemmycalak.model.MovieModel
import com.jemmycalak.model.ReviewMovie
import com.jemmycalak.model.VideoMovie
import com.jemmycalak.remote.BuildConfig
import com.jemmycalak.remote.api.MovieAPI
import com.jemmycalak.remote.utils.Constans.API_KEY
import com.jemmycalak.remote.utils.Constans.NOW_PLAYING_MOVIE_URL
import com.jemmycalak.remote.utils.Constans.PAGE
import com.jemmycalak.remote.utils.Constans.POPULER_MOVIE
import com.jemmycalak.remote.utils.Constans.POPULER_MOVIE_URL
import com.jemmycalak.remote.utils.Constans.TOP_RATE_MOVIE
import com.jemmycalak.remote.utils.Constans.TOP_RATE_MOVIE_URL
import com.jemmycalak.remote.utils.Constans.UPCOMING_MOVIE
import com.jemmycalak.remote.utils.Constans.UPCOMING_MOVIE_URL
import retrofit2.Response

class MovieDataSource(val api:MovieAPI) {

    suspend fun getListMovie(filter:Int, page:Int): Response<MovieModel> {
        val query=HashMap<String, Any>()
        query[PAGE] = page
        query[API_KEY] = BuildConfig.API_KEY

        return when(filter){
            POPULER_MOVIE -> api.getListMovie(POPULER_MOVIE_URL, query)
            UPCOMING_MOVIE -> api.getListMovie(UPCOMING_MOVIE_URL, query)
            TOP_RATE_MOVIE -> api.getListMovie(TOP_RATE_MOVIE_URL, query)
            else -> api.getListMovie(NOW_PLAYING_MOVIE_URL, query)
        }
    }

    suspend fun getListReviewMovie(idMovie:String?):Response<ReviewMovie>{
        val query = HashMap<String, Any>()
        query[API_KEY] = BuildConfig.API_KEY
        return api.getReviewMovie(idMovie, query)
    }

    suspend fun getMovieTrailer(movieId:Int): Response<VideoMovie> {
        val query = HashMap<String, Any>()
        query[API_KEY] = BuildConfig.API_KEY
        return api.getMovieTrailerById(movieId, query)
    }
}