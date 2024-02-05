package com.rajit.worldheritages.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rajit.worldheritages.data.model.HeritageEntity

@Dao
interface HeritageDao {

    @Query("SELECT * FROM HeritageTable")
    fun getAllHeritages(): PagingSource<Int, HeritageEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<HeritageEntity>)

}