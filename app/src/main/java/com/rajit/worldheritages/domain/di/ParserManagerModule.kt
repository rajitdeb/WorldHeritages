package com.rajit.worldheritages.domain.di

import com.rajit.worldheritages.domain.util.ParserManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module

val parserManagerModule = module {
    single {
        ParserManager(assetsManager = get(), moshi = get())
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}