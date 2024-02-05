package com.rajit.worldheritages.viewmodel.di

import com.rajit.worldheritages.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelModule = module {

    viewModel {
        MainViewModel(repository = get())
    }

}