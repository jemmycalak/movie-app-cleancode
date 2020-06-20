package com.jemmycalak.model


import com.google.gson.annotations.SerializedName

data class ReviewMovie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<ResultReview>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)


data class ResultReview(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("url")
    val url: String?
)