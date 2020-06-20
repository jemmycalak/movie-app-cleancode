package com.jemmycalak.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jemmycalak.local.db.MovieTable
import com.jemmycalak.model.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieModel(result: MovieTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieList(result:Result?)

    @Query("SELECT * FROM movietable")
    suspend fun getMovie(): MovieTable?

    @Query("SELECT * FROM result WHERE filter =:filter")
    suspend fun getResultMovie(filter:Int):List<Result>?

    @Query("DELETE FROM movietable")
    fun removeMovieTable()

    @Query("DELETE FROM result")
    fun removeMovieResult()

    @Query("UPDATE result SET isFavorit =:result WHERE id =:id")
    fun updateFavorite(result: Boolean?, id: Int?)

    @Query("SELECT * FROM result WHERE id=:id")
    fun getResult(id:Int?):LiveData<Result>

    @Query("SELECT * FROM result WHERE isFavorit=:favorite")
    fun getListFavorite(favorite:Boolean):LiveData<List<Result>>

}