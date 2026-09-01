package com.example.peego

import android.app.Application
import org.osmdroid.config.Configuration

class PeeGoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().load(
            this,
            getSharedPreferences("osmdroid_prefs", MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = "PeeGoApp/1.0"
    }
}
