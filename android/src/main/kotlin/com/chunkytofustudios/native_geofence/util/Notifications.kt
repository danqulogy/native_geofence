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
            return createForegroundServiceNotification(context)
        }

        fun createForegroundServiceNotification(context: Context): Notification {
            // Use a completely separate code path for Android 7
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                return createPreOreoNotification(context)
            }
            
            // This code will never be loaded on Android 7 devices
            return createOreoNotification(context)
        }
        
        // This is the ONLY method that will be called on Android 7 (API 24-25) devices
        private fun createPreOreoNotification(context: Context): Notification {
            Log.d(TAG, "Creating Android 7 notification (no channels)") 
            
            @SuppressLint("DiscouragedApi")
            val imageId = context.resources.getIdentifier("ic_launcher", "mipmap", context.packageName)
            val iconId = if (imageId != 0) imageId else android.R.drawable.ic_dialog_info
            
            // Create notification without any channel ID - this is safe for Android 7
            return NotificationCompat.Builder(context, "")
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_TEXT)
                .setSmallIcon(iconId)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
        }
        
        // This method is NEVER called on Android 7, so it's safe to use Android 8+ APIs
        private fun createOreoNotification(context: Context): Notification {
            Log.d(TAG, "Creating Android 8+ notification with channels")
            
            // Since this code never runs on Android 7, it's safe to use NotificationChannel
            // But we'll still add a safety check just to be sure
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                @SuppressLint("DiscouragedApi")
                val imageId = context.resources.getIdentifier("ic_launcher", "mipmap", context.packageName)
                val iconId = if (imageId != 0) imageId else android.R.drawable.ic_dialog_info
                
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                
                // Create notification channel
                // This is safe because this method is never called on Android 7
                val channel = android.app.NotificationChannel(
                    CHANNEL_ID,
                    "Geofence Events",
                    NotificationManager.IMPORTANCE_LOW
                )
                notificationManager.createNotificationChannel(channel)
                
                // Return the notification with channel ID
                return NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(NOTIFICATION_TITLE)
                    .setContentText(NOTIFICATION_TEXT)
                    .setSmallIcon(iconId)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build()
            } else {
                // This should never happen due to our version check in the caller
                // But just in case, return a safe notification
                return createPreOreoNotification(context)
            }
        }
    }
}
