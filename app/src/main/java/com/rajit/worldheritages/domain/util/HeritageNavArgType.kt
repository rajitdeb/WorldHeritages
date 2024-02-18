package com.rajit.worldheritages.domain.util

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.rajit.worldheritages.data.model.HeritageEntity

// For Passing Custom Parcelable Argument Type to Navigation Argument
class HeritageNavArgType: NavType<HeritageEntity>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): HeritageEntity? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): HeritageEntity {
        return Gson().fromJson(value, HeritageEntity::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: HeritageEntity) {
        bundle.putParcelable(key, value)
    }


}