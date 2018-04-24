package com.irontec.roomexample.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.irontec.roomexample.database.daos.TriviaCategoriesDao
import com.irontec.roomexample.database.daos.TriviaUserDao
import com.irontec.roomexample.database.entities.DBTriviaCategories
import com.irontec.roomexample.database.entities.DBTriviaUser

/**
 * Created by axier on 7/2/18.
 */

@Database(
        entities = [
            (DBTriviaUser::class),
            (DBTriviaCategories::class)
        ], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): TriviaUserDao

    abstract fun categoriesDao(): TriviaCategoriesDao

    companion object {

        /**
         * The only instance
         */
        private var sInstance: AppDatabase? = null

        /**
         * Gets the singleton instance of SampleDatabase.
         *
         * @param context The context.
         * @return The singleton instance of SampleDatabase.
         */
        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room
                        .databaseBuilder(context.applicationContext, AppDatabase::class.java, "trivia")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return sInstance!!
        }
    }

}
