package com.jemmycalak.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jemmycalak.local.dao.MovieDao
import com.jemmycalak.local.db.MovieTable
import com.jemmycalak.model.Result

@Database(entities = [
    MovieTable::class,
    Result::class
],
version = BuildConfig.DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                BuildConfig.DB_NAME
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}