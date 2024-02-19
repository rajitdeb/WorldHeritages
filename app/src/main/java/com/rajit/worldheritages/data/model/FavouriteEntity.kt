package com.rajit.worldheritages.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rajit.worldheritages.util.Constants

@Entity(tableName = Constants.FAVOURITE_TABLE_NAME)
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    val year: Int,
    val target: String,
    val name: String,
    val type: String,
    val region: String,
    val regionLong: String,
    val coordinates: String?,
    val lat: Double,
    val lng: Double,
    val page: String,
    val image: String,
    val imageAuthor: String,
    val shortInfo: String,
    val longInfo: String?
)
