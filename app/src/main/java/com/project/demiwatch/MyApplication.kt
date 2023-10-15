package com.project.demiwatch

import android.app.Application
import com.project.demiwatch.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}