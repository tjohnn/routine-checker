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

class BackgroundAlarmSchedulerService : AlarmSchedulerService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val routineId = intent?.getIntExtra(ARG_ROUTINE_ID, 0) ?: 0
        scheduleNextReminder(routineId)
        return Service.START_STICKY
    }

    companion object {
        const val ARG_ROUTINE_ID = "routine_id"

        fun startSelf(context: Context, lastRemindedRoutineId: Int = 0) {
            val intent = Intent(context, BackgroundAlarmSchedulerService::class.java)
            intent.putExtra(ARG_ROUTINE_ID, lastRemindedRoutineId)
            context.startService(intent)
        }

    }
}