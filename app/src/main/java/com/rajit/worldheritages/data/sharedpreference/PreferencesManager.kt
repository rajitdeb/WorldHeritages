package com.rajit.worldheritages.data.sharedpreference

import android.content.Context
import com.rajit.worldheritages.util.Constants

class PreferencesManager(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(
            Constants.USER_FILTER_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

    fun saveCountryAndTagData(countryValue: String, tagValue: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.USER_COUNTRY_PREFERENCE_KEY, countryValue)
        editor.putString(Constants.USER_TAG_PREFERENCE_KEY, tagValue)
        editor.apply()
    }

    fun getCountryAndTagData(): Pair<String, String> {
        val countryValue = sharedPreferences.getString(
            Constants.USER_COUNTRY_PREFERENCE_KEY,
            Constants.DEFAULT_COUNTRY_FILTER
        )

        val tagValue = sharedPreferences.getString(
            Constants.USER_TAG_PREFERENCE_KEY,
            Constants.DEFAULT_TAG_FILTER
        )

        return Pair(countryValue!!, tagValue!!)
    }

    fun resetUserCountryTagPreferences() {
        val editor = sharedPreferences.edit()
        editor.remove(Constants.USER_COUNTRY_PREFERENCE_KEY)
        editor.remove(Constants.USER_TAG_PREFERENCE_KEY)
        editor.apply()
    }

}