package com.jemmycalak.model

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieModel(

    @SerializedName("page")
    var page: Int?=0,

    @SerializedName("results")
    var results: List<Result>? = listOf(),

    @SerializedName("total_pages")
    var totalPages: Int?=0,

    @SerializedName("total_results")
    var totalResults: Int?=0
)

@Parcelize
@Entity
data class Result(
    @PrimaryKey
    @SerializedName("id")
    var id: Int?=0,
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("video")
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    @SerializedName("vote_count")
    var voteCount: Int?
):Parcelable{
    var isFavorit:Boolean=false
    var filter:Int=0
}