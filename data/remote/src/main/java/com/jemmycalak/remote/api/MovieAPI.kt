package com.jemmycalak.remote.api

import com.jemmycalak.model.MovieModel
import com.jemmycalak.model.ReviewMovie
import com.jemmycalak.model.VideoMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MovieAPI {

    @GET("movie/{filter}")
    suspend fun getListMovie(
        @Path("filter") filter: String,
        @QueryMap query: HashMap<String, Any>
    ): Response<MovieModel>

    @GET("movie/{idmovie}/reviews")
    suspend fun getReviewMovie(
        @Path("idmovie") id: String?,
        @QueryMap query: HashMap<String, Any>
    ): Response<ReviewMovie>

    @GET("movie/{movie_id}/videos?")
    suspend fun getMovieTrailerById(
        @Path("movie_id") movieId: Int,
        @QueryMap query: HashMap<String, Any>
    ): Response<VideoMovie>

}