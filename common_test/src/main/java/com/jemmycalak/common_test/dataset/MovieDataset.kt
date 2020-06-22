package com.jemmycalak.common_test.dataset

import com.jemmycalak.model.MovieModel
import com.jemmycalak.model.Result
import com.jemmycalak.model.ResultReview
import com.jemmycalak.model.ReviewMovie

object MovieDataset {

    val FAKE_MOVIERESULT = listOf(
        Result(1, true, null, null,null, null,
        null, null,null, null,null, null,null),
        Result(1, true, null, null,null, null,
            null, null,null, null,null, null,null))

    val FAKE_MOVIES = MovieModel(1, FAKE_MOVIERESULT, 100, 10)

    val FAKE_REVIEWRESULT = listOf(
        ResultReview("author1",null,null,null),
        ResultReview("author2",null,null,null)
    )

    val FAKE_REVIEW = ReviewMovie(null,null, FAKE_REVIEWRESULT,10, 100)
}