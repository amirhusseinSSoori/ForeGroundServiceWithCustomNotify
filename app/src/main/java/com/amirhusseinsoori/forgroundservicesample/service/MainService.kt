package com.amirhusseinsoori.forgroundservicesample.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.amirhusseinsoori.forgroundservicesample.*
import java.util.*

class MainService : Service() {
    //for iniiazliz notification
    private lateinit var notification: Notification
    private lateinit var remoteViews: RemoteViews
    private lateinit var expanded: RemoteViews
    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        setCustomNotify()
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(FOREGROUND_MAIN_SERVICE_ID, showNotification())
        if (!isNotificationVisible())
            with(NotificationManagerCompat.from(this@MainService)) {
                notificationBuilder.setCustomContentView(remoteViews)
                notify(FOREGROUND_MAIN_SERVICE_ID, notification)
            }
    }

    private fun isNotificationVisible(): Boolean {
        val notificationIntent = Intent(this, MainService::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            MAI4N_SERVICE_NOTIFICATION_REQUEST_CODE,
            notificationIntent,
            PendingIntent.FLAG_NO_CREATE
        )
        return pendingIntent != null
    }

    private fun setCustomNotify() {
        remoteViews = RemoteViews(packageName, R.layout.notification)
        expanded = RemoteViews(packageName, R.layout.notification_expanded)
        notificationBuilder.setCustomBigContentView(remoteViews)
        notificationBuilder.setCustomContentView(expanded)

        with(NotificationManagerCompat.from(this)) {
            notify(FOREGROUND_MAIN_SERVICE_ID, notificationBuilder.build())
        }

    }


    private fun showNotification(): Notification {
        val message = "monitoring device parameters"

        remoteViews = RemoteViews(packageName, R.layout.notification)
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent =
            PendingIntent.getActivity(
                this,
                MAI4N_SERVICE_NOTIFICATION_REQUEST_CODE, intent, 0
            )




        notificationBuilder = NotificationCompat.Builder(
            this,
            CHANNEL_ID_SERVICE
        )

        notificationBuilder.setContentTitle(getString(R.string.app_name))
            .setContentText(message)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_vpn1)
        this.notification = notificationBuilder.build()
        return notification
    }
}