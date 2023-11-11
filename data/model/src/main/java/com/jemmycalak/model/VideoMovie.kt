package com.jemmycalak.model

import com.google.gson.annotations.SerializedName

data class VideoMovie(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("results")
    val results: List<VideoResult?> = emptyList()
)

data class VideoResult(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("iso_3166_1")
    val iso31661: String? = "",
    @SerializedName("iso_639_1")
    val iso6391: String? = "",
    @SerializedName("key")
    val key: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("official")
    val official: Boolean? = false,
    @SerializedName("published_at")
    val publishedAt: String? = "",
    @SerializedName("site")
    val site: String? = "",
    @SerializedName("size")
    val size: Int? = 0,
    @SerializedName("type")
    val type: String? = ""
)