package com.android.infyassignment

import android.app.Application
import org.koin.android.ext.android.startKoin

class ApplicationLevel : Application() {

    override fun onCreate() {
        super.onCreate()
        // For DI
        startKoin(
            this,
            listOf(mainModule),
            loadPropertiesFromFile = true
        )
    }



}