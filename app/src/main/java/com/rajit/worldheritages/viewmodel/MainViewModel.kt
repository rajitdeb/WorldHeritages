package com.rajit.worldheritages.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.domain.model.Resource
import com.rajit.worldheritages.domain.repository.Repository
import com.rajit.worldheritages.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _heritageList = MutableStateFlow<PagingData<HeritageEntity>>(PagingData.empty())
    val heritageList: StateFlow<PagingData<HeritageEntity>> = _heritageList.asStateFlow()

    // Fetches HeritageEntity List from DB
    fun fetchAllHeritages() = viewModelScope.launch(Dispatchers.IO) {

        val heritageList: Resource<PagingSource<Int, HeritageEntity>> = repository.fetchHeritageList()

        when (heritageList) {

            is Resource.Success -> {
                heritageList.data?.let {

                    Log.i("MainViewModel", "WorldHeritageLiveData: $it")

                    // Construct Pager
                    Pager(
                        config = PagingConfig(
                            pageSize = Constants.PAGE_SIZE,
                            enablePlaceholders = true
                        ),
                        pagingSourceFactory = PagingSourceFactory { heritageList.data }
                    ).flow.collect { pagedData ->

                        // Returning PagingData from the Pager for the consumer to consume
                        _heritageList.value = pagedData
                    }

                }
            }

            is Resource.Error -> {
                Log.e("Error", "Error: ${heritageList.message}")
            }

        }

    }

}