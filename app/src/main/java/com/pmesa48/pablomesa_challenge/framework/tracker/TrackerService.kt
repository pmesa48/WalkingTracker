package com.pmesa48.pablomesa_challenge.framework.tracker

import android.app.*
import android.app.Notification.VISIBILITY_SECRET
import android.app.NotificationManager.IMPORTANCE_MIN
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.pmesa48.pablomesa_challenge.R
import com.pmesa48.pablomesa_challenge.framework.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrackerService : Service() {

    companion object {
        const val STOP_ACTION = "STOP"
        const val NOTIFICATION_ID = 123
    }

    @Inject
    lateinit var trackerManager: TrackerManager

    private var notification: Notification? = null
    private var notificationManager: NotificationManager? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (trackerManager.hasToStop(intent)) {
            trackerManager.onServiceEnd()
            stopForeground(true)
            stopSelf()
        } else {
            trackerManager.onServiceStart()
            createNotification()
            startForeground(NOTIFICATION_ID, notification)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        trackerManager.onDestroy()
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager == null) {
                notificationManager =
                    this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            }
            createChannelAndChannelGroup()
            buildNotification()
        }
    }

    private fun buildNotification() {
        notification = NotificationCompat.Builder(this, getString(R.string.channel_id))
            .apply {
                color = ContextCompat.getColor(mContext, R.color.purple_200)
                priority = NotificationCompat.PRIORITY_DEFAULT
                setContentTitle(getString(R.string.notification_title))
                setTicker(getString(R.string.notification_ticker))
                setContentText(getString(R.string.notification_subtext))
                setSmallIcon(R.drawable.ic_baseline_directions_walk_24)
                setWhen(0)
                setOnlyAlertOnce(true)
                setContentIntent(getPendingIntent())
                setOngoing(true)
            }.build()
    }

    private fun createChannelAndChannelGroup() {
        with(notificationManager) {
            this?.createNotificationChannelGroup(
                NotificationChannelGroup(
                    getString(R.string.channel_group_id),
                    getString(R.string.channel_group_name)
                )
            )
            this?.createNotificationChannel(
                NotificationChannel(
                    getString(R.string.channel_id),
                    getString(R.string.channel_name),
                    IMPORTANCE_MIN
                ).apply {
                    enableLights(false)
                    lockscreenVisibility = VISIBILITY_SECRET
                }
            )
        }

    }

    private fun getPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}