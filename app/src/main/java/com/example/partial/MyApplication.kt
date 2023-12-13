package com.example.partial

import android.app.Application
import com.cloudinary.android.MediaManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        configureCloudinary()
    }

    private fun configureCloudinary() {
        val config: MutableMap<String, String> = HashMap()
        config["cloud_name"] = "universidad-autonoma-del-peru"
        config["api_key"] = "331437187859647"
        config["api_secret"] = "b_gGgZp6iirhz7abc9r9MNGhUOw"
        MediaManager.init(this, config)
    }
}