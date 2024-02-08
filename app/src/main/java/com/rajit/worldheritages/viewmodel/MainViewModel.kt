package com.rajit.worldheritages.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: Repository) : ViewModel() {

    // Fetches HeritageEntity List from DB
    fun fetchAllHeritages(): Flow<PagingData<HeritageEntity>> = repository.fetchPagedHeritageList()

    // Fetch all heritage sites based on Filters
    fun fetchAllHeritagesByFilter(
        country: String = "",
        tag: String = "",
        startYear: Int = 1901,
        endYear: Int = 2024
    ): Flow<PagingData<HeritageEntity>> =
        repository.fetchAllPagedHeritagesByFilter(country, tag, startYear, endYear)

}