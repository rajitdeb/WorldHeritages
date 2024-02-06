package com.rajit.worldheritages.domain.repository

import android.util.Log
import androidx.paging.PagingSource
import com.rajit.worldheritages.data.db.dao.HeritageDao
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.domain.model.Resource
import com.rajit.worldheritages.domain.util.ParserManager
import com.squareup.moshi.JsonDataException
import java.io.IOException

class Repository(
    private val parserManager: ParserManager,
    private val heritageDao: HeritageDao
) {

    fun fetchHeritageList(): Resource<PagingSource<Int, HeritageEntity>> = try {
        Resource.Success(heritageDao.getAllHeritages())
    } catch (e: JsonDataException) {
        Resource.Error(e.toString())
    } catch (e: IOException) {
        Resource.Error(e.toString())
    }

    fun fetchAllHeritagesByFilter(
        country: String,
        tag: String,
        startYear: Int = 1901, // Hardcoded Start Year from JSON Data
        endYear: Int = 2024 // Hardcoded Start Year from JSON Data
    ): Resource<PagingSource<Int, HeritageEntity>> {

        try {
            return if (tag.isEmpty() && country.isNotEmpty()) {
                Resource.Success(
                    heritageDao.getAllHeritagesByCountry(
                        country,
                        startYear,
                        endYear
                    )
                )
            } else if (country.isEmpty() && tag.isNotEmpty()) {
                Resource.Success(heritageDao.getAllHeritagesByTag(tag, startYear, endYear))
            } else if (country.isEmpty() && tag.isEmpty()) {
                Resource.Success(heritageDao.getAllHeritages())
            } else {
                Resource.Success(
                    heritageDao.getAllHeritagesByCountryAndTag(
                        country,
                        tag,
                        startYear,
                        endYear
                    )
                )
            }
        } catch (e: JsonDataException) {
            return Resource.Error(e.toString())
        } catch (e: IOException) {
            return Resource.Error(e.toString())
        }


    }

    /**
     * This function uses [ParserManager] to parse the JSON file and extract data in the form of {List<HeritageEntity>}
     * And then add this list to the DB Table
     */
    suspend fun insertAllHeritages() {
        val data = parserManager.parseAssetsToEntityList()
        Log.i("Repository", "Repository: insertAllHeritages: $data")
        heritageDao.insertAll(data)
    }
}