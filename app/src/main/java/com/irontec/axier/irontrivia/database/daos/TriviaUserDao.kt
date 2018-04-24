package com.irontec.roomexample.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.irontec.roomexample.database.entities.DBTriviaUser

/**
 * Created by axier on 7/2/18.
 */

@Dao
interface TriviaUserDao {

    @get:Query("SELECT * FROM DBTriviaUser")
    val all: List<DBTriviaUser>

    @Query("SELECT * FROM DBTriviaUser WHERE username = :name LIMIT 1")
    fun findByName(name: String): DBTriviaUser

    @Insert
    fun insert(user: DBTriviaUser)

    @Delete
    fun delete(user: DBTriviaUser)

}
