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
            Log.d(TAG, "Foreground service started with standard notification.")
        } catch (e: Exception) {
            // If that fails, create the simplest possible notification
            Log.e(TAG, "Error creating standard notification: ${e.message}", e)
            
            // Create the absolute minimum notification
            val fallbackNotification = androidx.core.app.NotificationCompat.Builder(this, "")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Running")
                .build()
                
            startForeground(NOTIFICATION_ID, fallbackNotification)
            Log.d(TAG, "Foreground service started with emergency fallback notification.")
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
