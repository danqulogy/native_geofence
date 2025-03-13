package com.chunkytofustudios.native_geofence.util

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.util.Log

class Notifications {
    companion object {
        private const val TAG = "NativeGeofenceNotif"
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
            
            // Create notification builder - use empty channel ID for Android 7
            val builder = NotificationCompat.Builder(
                context, 
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) CHANNEL_ID else ""
            )
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_TEXT)
                .setSmallIcon(imageId)
                .setPriority(NotificationCompat.PRIORITY_LOW)
            
            // Create notification channel for Android 8.0+ (API 26+) using reflection
            // This ensures NotificationChannel is never directly referenced in the code
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    // Use reflection to create a NotificationChannel without directly referencing the class
                    val channelClass = Class.forName("android.app.NotificationChannel")
                    val channelConstructor = channelClass.getDeclaredConstructor(
                        String::class.java, 
                        CharSequence::class.java, 
                        Int::class.java
                    )
                    
                    // IMPORTANCE_LOW = 2
                    val channel = channelConstructor.newInstance(
                        CHANNEL_ID, 
                        "Geofence Events", 
                        NotificationManager.IMPORTANCE_LOW
                    )
                    
                    // Call createNotificationChannel via reflection
                    val createChannelMethod = notificationManager.javaClass.getMethod(
                        "createNotificationChannel", 
                        channelClass
                    )
                    createChannelMethod.invoke(notificationManager, channel)
                    
                    Log.d(TAG, "Created notification channel via reflection")
                } catch (e: Exception) {
                    // Log any reflection errors but continue - notification will still work
                    Log.e(TAG, "Error creating notification channel via reflection: ${e.message}", e)
                }
            } else {
                // For older versions (API 25 and below), we just set the priority
                builder.priority = Notification.PRIORITY_LOW
                Log.d(TAG, "Using legacy notification without channels for Android ${Build.VERSION.SDK_INT}")
            }
            
            return builder.build()
        }
    }
}
