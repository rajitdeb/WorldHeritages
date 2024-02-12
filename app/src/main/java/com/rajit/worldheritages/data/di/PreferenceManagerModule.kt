package com.rajit.worldheritages.data.di

import android.content.Context
import com.rajit.worldheritages.data.sharedpreference.PreferencesManager
import org.koin.core.module.Module
import org.koin.dsl.module

val preferenceManagerModule: (Context) -> Module = { context ->
    module {
        single { PreferencesManager(context) }
    }
}