package com.chunkytofustudios.native_geofence.util

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
// Don't import NotificationChannel at the top level since it's only available on API 26+

class Notifications {
    companion object {
        private const val CHANNEL_ID = "native_geofence_plugin_channel"
        private const val NOTIFICATION_TITLE = "Processing geofence event."
        private const val NOTIFICATION_TEXT = "We noticed you are near a key location and are checking if we can help."

        fun createBackgroundWorkerNotification(context: Context): Notification {
            // Background Worker notification is only needed for Android 30 and below (30% of users
            // as of Jan 2025), so we are re-using the Foreground Service notification.
            return createForegroundServiceNotification(context)
        }

        // TODO: Make notification details customizable by plugin user.
        fun createForegroundServiceNotification(context: Context): Notification {
            @SuppressLint("DiscouragedApi") // Can't use R syntax in Flutter plugin.
            val imageId = context.resources.getIdentifier("ic_launcher", "mipmap", context.packageName)
            
            // Get the notification manager
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // Create notification builder
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_TEXT)
                .setSmallIcon(imageId)
                .setPriority(NotificationCompat.PRIORITY_LOW)
            
            // Create notification channel for Android 8.0+ (API 26+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Import and use NotificationChannel only when needed
                val channel = android.app.NotificationChannel(
                    CHANNEL_ID,
                    "Geofence Events",
                    NotificationManager.IMPORTANCE_LOW
                )
                notificationManager.createNotificationChannel(channel)
            } else {
                // For older versions (API 25 and below), we just set the priority
                // using the old method and don't use notification channels
                builder.priority = Notification.PRIORITY_LOW
            }
            
            return builder.build()
        }
    }
}
