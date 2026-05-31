package com.solara.pofindr.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solara.pofindr.data.model.PostOffice

@Dao
interface PostOfficeDao {

    @Query("SELECT * FROM post_office")
    fun getAllPostOffices(): LiveData<List<PostOffice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(postOffice: List<PostOffice>)
}