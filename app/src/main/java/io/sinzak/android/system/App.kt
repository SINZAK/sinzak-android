package io.sinzak.android.system

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    lateinit var prefs : PreferenceUtil

    override fun onCreate() {
        super.onCreate()


        prefs = PreferenceUtil(this)
    }
}