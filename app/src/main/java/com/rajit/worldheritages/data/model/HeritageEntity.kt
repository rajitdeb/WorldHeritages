package com.rajit.worldheritages.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rajit.worldheritages.util.Constants
import kotlinx.parcelize.Parcelize

/**
 * Structure of JSON Item
 *
 * "id": "3",
 * "year": 1978,
 * "target": "DEU",
 * "name": "Aachen Cathedral ",
 * "type": "Cultural",
 * "region": "EUR",
 * "regionLong": "Europe and North America",
 * "coordinates": "N50 46 28 E6 5 4",
 * "lat": 50.77444444444444,
 * "lng": 6.084444444444444,
 * "page": "http://whc.unesco.org/en/list/3",
 * "image": "https://whc.unesco.org/uploads/thumbs/site_0003_0001-750-750-20151104122109.jpg",
 * "imageAuthor": "Aachen Cathedral © Mario Santana ",
 * "shortInfo": "Aachen Cathedral \n\nConstruction of this palatine chapel, with its octagonal basilica and cupola, began c. 790–800 under the Emperor Charlemagne.",
 * "longInfo": "With"
 */

@Parcelize
@Entity(tableName = Constants.TABLE_NAME)
data class HeritageEntity(
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
): Parcelable
