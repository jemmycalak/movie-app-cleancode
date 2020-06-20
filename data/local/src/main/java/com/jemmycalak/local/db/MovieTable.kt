package com.jemmycalak.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieTable(
    @PrimaryKey
    var page: Int?,
    var totalPages: Int?,
    var totalResults: Int?,
    var date:String?
)