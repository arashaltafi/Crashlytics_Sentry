package com.arash.altafi.crashlytics

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        CrashlyticsUtils.setupCrashlytics(
            this,
            BuildConfig.DEBUG,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )
    }
}