package com.tjohnn.routinechecks.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class ReminderBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(AlarmSchedulerService.ARG_ROUTINE_TITLE)
            ?: return
        val id = intent.getIntExtra(AlarmSchedulerService.ARG_ROUTINE_ID, 0)
        val mainActivityIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            mainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val soundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, createNotificationChannel(context))
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.routine)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLights(15859712, 400, 600)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(soundUri, AudioManager.STREAM_ALARM)
            .setContentTitle(context.getString(R.string.next_routine))
            .setContentText(context.getString(R.string.next_routine_message, title))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(context.getString(R.string.next_routine_message, title))
            )

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(generateNotificationId(), builder.build())

        // start service to schedule next reminder
        ForegroundAlarmSchedulerService.startSelf(context, id)
    }

    private fun generateNotificationId(): Int {
        val now = Date()
        return SimpleDateFormat("HHmmssSS", Locale.US).format(now).toInt()
    }

    private fun createNotificationChannel(context: Context): String {
        val channelId = AlarmSchedulerService.REMINDER_NOTIFICATION_CHANNEL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                channelId,
                AlarmSchedulerService.REMINDER_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return channelId
    }

}