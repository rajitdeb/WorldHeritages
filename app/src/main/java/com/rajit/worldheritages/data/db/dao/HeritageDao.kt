package com.rajit.worldheritages.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rajit.worldheritages.data.model.FavouriteEntity
import com.rajit.worldheritages.data.model.HeritageEntity
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM FavouritesTable")
    fun fetchAllFavourites(): Flow<List<FavouriteEntity>>

    @Query("SELECT COUNT(*) FROM FavouritesTable WHERE id = :heritageID")
    fun getFavouriteByID(heritageID: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<HeritageEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveToFavourites(favouriteEntity: FavouriteEntity)

    @Delete
    suspend fun deleteFavourite(favouriteEntity: FavouriteEntity)

}