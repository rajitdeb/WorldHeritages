package com.rajit.worldheritages.domain.di

import com.rajit.worldheritages.domain.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        Repository(
            parserManager = get(),
            heritageDao = get()
        )
    }
}