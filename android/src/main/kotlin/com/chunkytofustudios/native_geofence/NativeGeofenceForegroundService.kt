package com.chunkytofustudios.native_geofence

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import android.os.Build
import android.util.Log
import com.chunkytofustudios.native_geofence.util.Notifications
import kotlin.time.Duration.Companion.minutes

// TODO: Allow customizing notification details.
class NativeGeofenceForegroundService : Service() {
    companion object {
        @JvmStatic
        private val TAG = "NativeGeofenceForegroundService"

        // TODO: Consider using random ID.
        private const val NOTIFICATION_ID = 938130

        private val WAKE_LOCK_TIMEOUT = 5.minutes
    }

    override fun onBind(p0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        
        // Acquire wake lock first to ensure it happens regardless of notification success
        (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Constants.ISOLATE_HOLDER_WAKE_LOCK_TAG).apply {
                setReferenceCounted(false)
                acquire(WAKE_LOCK_TIMEOUT.inWholeMilliseconds)
            }
        }
        
        // Try to create and start with the normal notification
        try {
            val notification = Notifications.createForegroundServiceNotification(this)
            startForeground(NOTIFICATION_ID, notification)
            Log.d(TAG, "Foreground service started with notification ID=$NOTIFICATION_ID.")
        } catch (e: Exception) {
            // If that fails (e.g., on Android 7), create a simpler notification
            Log.e(TAG, "Error creating foreground notification: ${e.message}", e)
            try {
                // For Android 7, don't use notification channels
                val fallbackNotification = androidx.core.app.NotificationCompat.Builder(this, 
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) "fallback_channel" else "")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("App is running")
                    .setPriority(androidx.core.app.NotificationCompat.PRIORITY_LOW)
                    .build()
                    
                startForeground(NOTIFICATION_ID, fallbackNotification)
                Log.d(TAG, "Foreground service started with fallback notification.")
            } catch (fallbackException: Exception) {
                // Last resort - create the most basic notification possible
                Log.e(TAG, "Error creating fallback notification: ${fallbackException.message}", fallbackException)
                startForeground(NOTIFICATION_ID, 
                    androidx.core.app.NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Running")
                        .build()
                )
                Log.d(TAG, "Foreground service started with basic fallback notification.")
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent.action == Constants.ACTION_SHUTDOWN) {
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Constants.ISOLATE_HOLDER_WAKE_LOCK_TAG).apply {
                    if (isHeld) {
                        release()
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(STOP_FOREGROUND_REMOVE)
            } else {
                stopForeground(true)
            }
            stopSelf()
            Log.d(TAG, "Foreground service stopped.")
        }
        return START_STICKY
    }
}
