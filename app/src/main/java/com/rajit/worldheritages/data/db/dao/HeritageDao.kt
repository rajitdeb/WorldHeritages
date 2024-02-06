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

    @Query("SELECT * FROM HeritageTable WHERE target = :country AND year >= :startYear AND year <= :endYear")
    fun getAllHeritagesByCountry(
        country: String,
        startYear: Int,
        endYear: Int
    ): PagingSource<Int, HeritageEntity>

    @Query("SELECT * FROM HeritageTable WHERE type = :tag AND year >= :startYear AND year <= :endYear")
    fun getAllHeritagesByTag(
        tag: String,
        startYear: Int,
        endYear: Int
    ): PagingSource<Int, HeritageEntity>

    @Query("SELECT * FROM HeritageTable WHERE target = :country AND type = :tag " +
            "AND year >= :startYear AND year <= :endYear")
    fun getAllHeritagesByCountryAndTag(
        country: String,
        tag: String,
        startYear: Int,
        endYear: Int
    ): PagingSource<Int, HeritageEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<HeritageEntity>)

}