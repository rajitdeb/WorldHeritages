package com.rajit.worldheritages.di

import com.rajit.worldheritages.WorldHeritageApplication
import org.koin.core.module.Module
import org.koin.dsl.module


val assetManagerModule: (application: WorldHeritageApplication) -> Module = { application ->
    module{
        single { application.assets }
    }
}