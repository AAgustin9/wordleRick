package com.example.wordlerick.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

class NotificationHelper(private val context: Context) {
    companion object {
        // Default to 24 hours
        const val DEFAULT_INTERVAL_MILLIS = 24L * 60 * 60 * 1000
        const val CHANNEL_ID = "reminder_channel"
        private const val CHANNEL_NAME = "Daily Reminder"
        private const val CHANNEL_DESC = "Notifications to remind playing the game"
    }

    fun scheduleReminder(intervalMillis: Long = DEFAULT_INTERVAL_MILLIS) {
        createNotificationChannel()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        // Inexact repeating for battery
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + intervalMillis,
            intervalMillis,
            pendingIntent
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESC
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
} 