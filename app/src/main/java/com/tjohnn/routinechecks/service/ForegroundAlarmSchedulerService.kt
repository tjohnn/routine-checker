package com.tjohnn.routinechecks.service


import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.data.Result
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import com.tjohnn.routinechecks.data.repository.RoutineRepository
import com.tjohnn.routinechecks.ui.MainActivity
import com.tjohnn.routinechecks.utils.computeNextRoutineTimeFromLast
import com.tjohnn.routinechecks.utils.computeNextRoutineTimeFromNow
import dagger.android.AndroidInjection
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ForegroundAlarmSchedulerService : AlarmSchedulerService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startForegroundNotification()
        val routineId = intent?.getIntExtra(ARG_ROUTINE_ID, 0) ?: 0
        scheduleNextReminder(routineId)
        Timber.d("Schedule Service started")
        return Service.START_STICKY
    }

    private fun startForegroundNotification() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        val builder = NotificationCompat.Builder(this, createForeGroundNotificationChannel())
            .setTicker(getString(R.string.scheduling))
            .setContentTitle(getString(R.string.scheduling))
            .setContentText(getString(R.string.setting_next_reminder))
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    mainActivityIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setDefaults(NotificationCompat.DEFAULT_SOUND and NotificationCompat.DEFAULT_VIBRATE)
            .setOngoing(true)
        startForeground(1, builder.build())
    }

    private fun createForeGroundNotificationChannel(): String {
        val channelId = SCHEDULING_NOTIFICATION_CHANNEL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel =
                NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(false)
            notificationChannel.setShowBadge(false)
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return channelId
    }

    companion object {

        const val SCHEDULING_NOTIFICATION_CHANNEL = "scheduling"
        const val CHANNEL_NAME = "SchedulingReminders"
        const val ARG_ROUTINE_ID = "routine_id"

        fun startSelf(context: Context, lastRemindedRoutineId: Int = 0) {
            val intent = Intent(context, ForegroundAlarmSchedulerService::class.java)
            intent.putExtra(ARG_ROUTINE_ID, lastRemindedRoutineId)
            ContextCompat.startForegroundService(context, intent)
        }

    }
}