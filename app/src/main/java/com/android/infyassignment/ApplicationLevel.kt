package com.android.infyassignment

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ApplicationLevel : Application() {
    override fun onCreate() {
        super.onCreate()
        // For DI
        startKoin {
            androidContext(this@ApplicationLevel)
            modules(listOf(mainModule))
        }
    }


}