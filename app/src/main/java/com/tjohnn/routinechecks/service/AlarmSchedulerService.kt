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

open class AlarmSchedulerService : LifecycleService() {

    @Inject
    lateinit var routineRepository: RoutineRepository

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    /**
     * Schedules next reminder for user
     *
     * @param lastRemindedRoutineId The id of the routine that user was reminded about,
     * it is needed to avoid rescheduling reminder for it
     *
     */
    protected fun scheduleNextReminder(lastRemindedRoutineId: Int) {
        coroutineScope.launch {

            // update last reminded routine to pending
            if(lastRemindedRoutineId != 0) {
                val routineResult = routineRepository.getRoutineById(lastRemindedRoutineId)
                if(routineResult is Result.Success) {
                    val routine = routineResult.data
                    routine.previousCheckTime = routine.nextCheckTime
                    routine.nextCheckTime = computeNextRoutineTimeFromLast(routine).time
                    // if routine is still pending, it means the previous time check was missed
                    if(routine.isPending) {
                        routine.numberOfMissed++
                    }
                    routine.isPending = true

                    routineRepository.saveRoutine(routine)
                }
            }

            // Update all routines whose check time has elapsed, i.e last reminder time has passed 5 minutes
            val elapsedRoutines = routineRepository.getElapsedRoutines()
            Timber.d("Updating routines: $elapsedRoutines")
            Throwable().printStackTrace()
            if (elapsedRoutines.isNotEmpty()) {

                val newList = elapsedRoutines.map {
                    it.isPending = false
                    it.numberOfMissed++
                    it
                }
                routineRepository.updateAll(newList)
                Timber.d("Updated to: $newList")

            }

            // schedule next reminder
            val nextRoutine = routineRepository.getNextRoutineForReminder()
            Timber.d("Next routine: $nextRoutine")
            if (nextRoutine != null) {
                scheduleReminder(nextRoutine)
            }

            // stop this service after scheduling
            stopSelf()
        }
    }

    private fun scheduleReminder(nextRoutine: Routine) {
        // schedule next alarm
        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderBroadcastReceiver::class.java)
        intent.putExtra(ARG_ROUTINE_ID, nextRoutine.id)
        intent.putExtra(ARG_ROUTINE_TITLE, nextRoutine.title)
        val pendingIntent =
            PendingIntent.getBroadcast(baseContext, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance()
        calendar.time = nextRoutine.nextCheckTime
        // notify 5 minutes before time
        calendar.add(Calendar.MINUTE, -5)

        alarmMgr.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    override fun onDestroy() {
        coroutineScope.coroutineContext.cancel()
        super.onDestroy()
    }

    companion object {
        const val REMINDER_NOTIFICATION_CHANNEL = "reminder"
        const val REMINDER_CHANNEL_NAME = "ReminderNotificationChannel"
        const val ARG_ROUTINE_TITLE = "routine_title"
        const val ARG_ROUTINE_ID = "routine_id"
    }
}