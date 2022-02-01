package com.example.news2.presentation.ui

import android.app.Application
import com.example.news2.data.shared.SharedPref
import dagger.hilt.android.HiltAndroidApp

val prefs: SharedPref by lazy {
       BaseApplication.prefs!!
}
@HiltAndroidApp
class BaseApplication: Application() {
    companion object {
        var prefs: SharedPref? = null
        lateinit var instance: BaseApplication
                 private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = SharedPref(applicationContext)
    }
}