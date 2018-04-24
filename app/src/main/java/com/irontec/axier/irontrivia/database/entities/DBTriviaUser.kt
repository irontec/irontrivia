package com.irontec.roomexample.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by axier on 7/2/18.
 */

@Entity
class DBTriviaUser {

    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null

    @ColumnInfo(name = "username")
    var username: String? = null

    @ColumnInfo(name = "token")
    var token: String? = null
}
