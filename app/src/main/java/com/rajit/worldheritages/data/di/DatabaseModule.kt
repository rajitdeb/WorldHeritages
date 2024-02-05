package com.rajit.worldheritages.data.di

import com.rajit.worldheritages.data.db.WorldHeritageDatabase
import org.koin.dsl.module

val databaseModule= module {

    // Singleton Instance of DB Instance
    single {
        WorldHeritageDatabase.getDatabase(get())
    }

    // Singleton Instance of DAO
    single {
        get<WorldHeritageDatabase>().heritageDao()
    }

}