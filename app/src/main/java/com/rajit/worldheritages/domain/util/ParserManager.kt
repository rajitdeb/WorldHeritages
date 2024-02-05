package com.rajit.worldheritages.domain.util

import android.content.res.AssetManager
import android.util.Log
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.domain.model.Heritage
import com.rajit.worldheritages.domain.model.toEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This class is responsible for extracting the data from JSON file
 * And convert the data to [HeritageEntity]
 *
 * @param assetsManager is injected by Koin, used for accessing assets folder
 * @param defaultDispatcher used for asynchronous execution
 */
class ParserManager(
    private val assetsManager: AssetManager,
    private val moshi: Moshi,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun parseAssetsToEntityList(): List<HeritageEntity> = withContext(defaultDispatcher) {

        // Using bufferedReader to read the JSON text from the file
        val bufferedReader = assetsManager.open("heritages.json").bufferedReader()
        val data = bufferedReader.use { it.readText() }

        // Specifying the type of data that Moshi needs to convert the text to
        val listType = Types.newParameterizedType(List::class.java, Heritage::class.java)
        val adapter: JsonAdapter<List<Heritage>> = moshi.adapter(listType)

        // Mapping the resultant data to HeritageEntity Model from Heritage Model
        val result = adapter.fromJson(data)?.map {
            Log.i("MyParserManager", "parseAssetsToEntityList: $it")
            it.toEntity()
        }

        return@withContext result ?: emptyList()
    }

}