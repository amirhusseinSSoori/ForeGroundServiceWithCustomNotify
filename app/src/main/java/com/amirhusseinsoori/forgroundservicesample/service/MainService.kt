package com.amirhusseinsoori.forgroundservicesample.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MainService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}