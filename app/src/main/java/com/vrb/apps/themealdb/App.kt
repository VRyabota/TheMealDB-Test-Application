package com.vrb.apps.themealdb

import android.app.Application
import com.vrb.apps.themealdb.di.appModule
import com.vrb.apps.themealdb.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)
            modules(networkModule, appModule)
        }
    }
}