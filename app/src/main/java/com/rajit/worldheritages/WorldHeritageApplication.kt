package com.rajit.worldheritages

import android.app.Application
import com.rajit.worldheritages.data.di.databaseModule
import com.rajit.worldheritages.data.di.preferenceManagerModule
import com.rajit.worldheritages.di.assetManagerModule
import com.rajit.worldheritages.domain.di.parserManagerModule
import com.rajit.worldheritages.domain.di.repositoryModule
import com.rajit.worldheritages.viewmodel.di.mainViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WorldHeritageApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@WorldHeritageApplication)
            modules(
                listOf(
                    databaseModule,
                    assetManagerModule(this@WorldHeritageApplication),
                    preferenceManagerModule(this@WorldHeritageApplication.applicationContext),
                    parserManagerModule,
                    repositoryModule,
                    mainViewModelModule,
                )
            )
        }

    }

}