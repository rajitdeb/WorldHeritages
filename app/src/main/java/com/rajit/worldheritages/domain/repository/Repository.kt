package com.rajit.worldheritages.domain.repository

import android.util.Log
import androidx.paging.PagingSource
import com.rajit.worldheritages.data.db.dao.HeritageDao
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.domain.util.ParserManager
import com.rajit.worldheritages.domain.model.Resource
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