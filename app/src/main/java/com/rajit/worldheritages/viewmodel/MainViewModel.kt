package com.rajit.worldheritages.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.rajit.worldheritages.data.model.FavouriteEntity
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.data.sharedpreference.PreferencesManager
import com.rajit.worldheritages.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository,
    private val preferenceManager: PreferencesManager
) : ViewModel() {

    // Fetch all heritage sites based on Filters
    fun fetchAllHeritagesByFilter(
        country: String = "",
        tag: String = "",
        startYear: Int = 1901,
        endYear: Int = 2024
    ): Flow<PagingData<HeritageEntity>> =
        repository.fetchAllPagedHeritagesByFilter(country, tag, startYear, endYear)

    fun fetchAllFavourites(): Flow<List<FavouriteEntity>> {
        return repository.fetchAllFavourites()
    }

    fun fetchFavouriteByID(heritageID: Int): Boolean {
        return repository.getFavouriteByID(heritageID)
    }

    fun saveCountryAndTagPreference(countryValue: String, tagValue: String) {
        preferenceManager.saveCountryAndTagData(countryValue, tagValue)
    }

    // Since we already have defined the Key in PreferenceManager
    // So this function doesn't accept the Key Parameter
    fun resetUserCountryAndTagPreference() {
        preferenceManager.resetUserCountryTagPreferences()
    }

    // Since we already have defined the Key in PreferenceManager
    // So this function doesn't accept the Key Parameter
    fun getCountryAndTagPreference(): Pair<String, String> {
        return preferenceManager.getCountryAndTagData()
    }

    fun saveToFavourites(favouriteEntity: FavouriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveToFavourites(favouriteEntity)
    }

    fun deleteFavourite(favouriteEntity: FavouriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFavourites(favouriteEntity)
    }

}