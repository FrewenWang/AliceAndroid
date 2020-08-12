package com.frewen.nyx.hilt.demo

import android.app.Application

class NyxHiltApp : Application() {

    lateinit var roomServiceProvider: RoomServiceProvider

    override fun onCreate() {
        super.onCreate()

        roomServiceProvider = RoomServiceProvider(this)
    }

}