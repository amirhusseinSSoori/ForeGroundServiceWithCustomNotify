package com.amirhusseinsoori.forgroundservicesample

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

class App:Application() {

    lateinit var channel: NotificationChannel
    lateinit var manger: NotificationManager


    lateinit var channelService: NotificationChannel
    lateinit var mangerService: NotificationManager
    override fun onCreate() {
        super.onCreate()

        createNotificationChannelService()
    }




    private fun createNotificationChannelService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelService = NotificationChannel(
                CHANNEL_ID_SERVICE,
                "MAINSERVICE",
                NotificationManager.IMPORTANCE_HIGH
            )

            mangerService = getSystemService(NotificationManager::class.java)
            mangerService.createNotificationChannel(channelService)
        }


    }
}