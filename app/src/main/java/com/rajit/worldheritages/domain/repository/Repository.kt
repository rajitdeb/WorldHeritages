package com.rajit.worldheritages.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rajit.worldheritages.data.db.dao.HeritageDao
import com.rajit.worldheritages.data.model.FavouriteEntity
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.domain.util.ParserManager
import com.rajit.worldheritages.util.Constants
import kotlinx.coroutines.flow.Flow

class Repository(
    private val parserManager: ParserManager,
    private val heritageDao: HeritageDao
) {

    fun fetchAllPagedHeritagesByFilter(
        country: String,
        tag: String,
        startYear: Int = 1901, // Hardcoded Start Year from JSON Data
        endYear: Int = 2024 // Hardcoded Start Year from JSON Data
    ): Flow<PagingData<HeritageEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE
            )
        ) {
            if (tag.isEmpty() && country.isNotEmpty()) {
                heritageDao.getAllHeritagesByCountry(country, startYear, endYear)
            } else if (country.isEmpty() && tag.isNotEmpty()) {
                heritageDao.getAllHeritagesByTag(tag, startYear, endYear)
            } else if(country.isEmpty() && tag.isEmpty()) {
                heritageDao.getAllHeritages()
            } else {
                heritageDao.getAllHeritagesByCountryAndTag(country, tag, startYear, endYear)
            }
        }.flow
    }

    fun fetchAllHeritagesBySearchQuery(query: String): Flow<PagingData<HeritageEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE
            )
        ) {
            heritageDao.getAllHeritagesBySearchQuery(query)
        }.flow
    }

    fun fetchAllFavourites(): Flow<List<FavouriteEntity>> {
        return heritageDao.fetchAllFavourites()
    }

    fun getFavouriteByID(heritageID: Int): Boolean {
        return heritageDao.getFavouriteByID(heritageID)
    }

    /**
     * This function uses [ParserManager] to parse the JSON file and extract data in the form of {List<HeritageEntity>}
     * And then add this list to the DB Table
     */
    suspend fun insertAllHeritages() {
        val data = parserManager.parseAssetsToEntityList()
//        Log.i("Repository", "Repository: insertAllHeritages: $data")
        heritageDao.insertAll(data)
    }

    suspend fun saveToFavourites(favouriteEntity: FavouriteEntity) {
        heritageDao.saveToFavourites(favouriteEntity)
    }

    suspend fun deleteFavourites(favouriteEntity: FavouriteEntity) {
        heritageDao.deleteFavourite(favouriteEntity)
    }
}