package com.css216.qazan

import android.app.Application
import com.css216.qazan.data.AppContainer
import com.css216.qazan.data.DefaultAppContainer

class QazanApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
