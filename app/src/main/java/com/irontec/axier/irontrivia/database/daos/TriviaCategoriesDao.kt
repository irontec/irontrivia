package com.irontec.roomexample.database.daos

import android.arch.persistence.room.*

import com.irontec.roomexample.database.entities.DBTriviaCategories

/**
 * Created by axier on 7/2/18.
 */

@Dao
interface TriviaCategoriesDao {

    @get:Query("SELECT * FROM DBTriviaCategories")
    val all: List<DBTriviaCategories>

    @Query("SELECT * FROM DBTriviaCategories WHERE name = :name")
    fun findByName(name: String): DBTriviaCategories

    @Query("SELECT * FROM DBTriviaCategories WHERE id = :id")
    fun findById(id: Int): DBTriviaCategories

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(categories: List<DBTriviaCategories>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<DBTriviaCategories>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(category: DBTriviaCategories)

    @Delete
    fun delete(category: DBTriviaCategories)

}
