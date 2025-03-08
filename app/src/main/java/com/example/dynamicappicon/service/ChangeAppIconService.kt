package com.example.dynamicappicon.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dynamicappicon.R
import com.example.dynamicappicon.viewModel.AppIconConfig

class ChangeAppIconService: Service() {

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return try {
            appIcon = intent?.getStringExtra("AppIconName") ?: AppIconConfig.DEFAULT.aliasName
            startForeground(
                1,
                createNotification("Your app icon will change when you restart the application") // Create a foreground notification
            )
            START_STICKY
        }catch (e : Exception){
            Log.d("Exception",e.stackTraceToString())
            START_NOT_STICKY
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        try {
            val inputData = Data.Builder()
                .putString("", appIcon)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<ChangeAppIconWorker>()
                .setInputData(inputData)
                .build()

            WorkManager.getInstance(this).enqueue(workRequest)

            // Stop the service after handling
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                stopForeground(STOP_FOREGROUND_REMOVE)
            } else {
                stopForeground(true)
            }
            stopSelf()
        }catch (e : Exception){
            Log.d("Exception", e.stackTraceToString())
            stopSelf()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        var appIcon: String? = null
        fun enableDisableActivityAlais(context: Context, aliasName: String?) {
            try {
                val appContext = context.applicationContext
                val packageManager = appContext.packageManager
                val aliases = AppIconConfig.values().map { it.aliasName }

                for (alias in aliases) {
                    packageManager.setComponentEnabledSetting(
                        ComponentName(appContext, alias),
                        if (alias == aliasName) {
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        } else {
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                        },
                        PackageManager.DONT_KILL_APP
                    )
                }
            } catch (e: Exception) {
                context.applicationContext.packageManager.setComponentEnabledSetting(
                    ComponentName(context.applicationContext, "${context.packageName}.Superman"),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
                )
            }
        }
    }

    private fun createNotification(content: String): Notification {
        val channelId = "app_monitor_service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "AppIconChannel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("DynamicIcon")
            .setContentText(content)
            .setSmallIcon(R.drawable.batman) // Replace with your app's icon
        .build()
    }

}
