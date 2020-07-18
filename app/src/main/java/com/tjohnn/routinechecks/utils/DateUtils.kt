package com.tjohnn.routinechecks.utils

import android.content.Context
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import java.util.*


/**
 * Computes the next time for routine to be carried out based on frequency and current time
 */
fun computeNextRoutineTimeFromNow(routine: Routine): Calendar {
    val calendar = Calendar.getInstance()
    when (routine.duration) {
        RoutineDuration.HOURLY -> {
            calendar.add(Calendar.HOUR, routine.frequency)
        }
        RoutineDuration.DAILY -> {
            calendar.add(Calendar.DAY_OF_MONTH, routine.frequency)
        }
        RoutineDuration.WEEKLY -> {
            calendar.add(Calendar.WEEK_OF_YEAR, routine.frequency)
        }
        RoutineDuration.MONTHLY -> {
            calendar.add(Calendar.MONTH, routine.frequency)
        }
        RoutineDuration.YEARLY -> {
            calendar.add(Calendar.YEAR, routine.frequency)
        }
    }
    return calendar
}

/**
 * Computes the next time for routine to be carried out based on frequency
 */
fun computeNextRoutineTimeFromLast(routine: Routine): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = routine.nextCheckTime
    when (routine.duration) {
        RoutineDuration.HOURLY -> {
            calendar.add(Calendar.MINUTE, routine.frequency)
        }
        RoutineDuration.DAILY -> {
            calendar.add(Calendar.DATE, routine.frequency)
        }
        RoutineDuration.WEEKLY -> {
            calendar.add(Calendar.WEEK_OF_YEAR, routine.frequency)
        }
        RoutineDuration.MONTHLY -> {
            calendar.add(Calendar.MONTH, routine.frequency)
        }
        RoutineDuration.YEARLY -> {
            calendar.add(Calendar.YEAR, routine.frequency)
        }
    }
    return calendar
}


/**
 * Computes the next time for routine to be carried out based on frequency
 */
fun Date.toFriendlyText(context: Context): String {
    val now = Calendar.getInstance()
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DAY_OF_MONTH, 1)

    val calendar2 = Calendar.getInstance()
    calendar2.time = this
    if (isTheSameDay(now, calendar2)) {
        return context.getString(R.string.today_at_x,
                String.format(Locale.ENGLISH, "%1\$tH:%1\$tM", this))
    } else if (isTheSameDay(tomorrow, calendar2)) {
        return context.getString(R.string.tomorrow_at_x,
                String.format(Locale.ENGLISH, "%1\$tH:%1\$tM", this))
    }
    return toReadableDateTime()
}

fun isTheSameDay(calendar: Calendar, calendar2: Calendar) = calendar.get(Calendar.DATE) == calendar2.get(Calendar.DATE) &&
        calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
        calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)

fun Date.toReadableDateTime(): String {
    return String.format(
            Locale.ENGLISH,
            "%1\$td %1\$tb %1\$tY %1\$tH:%1\$tM",
            this
    )
}


fun Date.toDurationText(context: Context): String {
    val difference =  this.time - System.currentTimeMillis()
    val oneMinute = 60 * 1000L
    val oneHour = 60 * 60 * 1000L
    val oneDay = oneHour * 24
    val oneMonth = oneDay * 30
    val oneYear = oneMonth * 12

    if(difference < 0) {
        return context.getString(R.string.you_are_late_by, "${-1 * difference / oneMinute }mins")
    }

    val duration =  when {
        difference <  oneHour -> {
            "${difference / oneMinute }mins"
        }
        difference <  oneDay -> {
            "${difference / oneHour }hrs"
        }
        difference <  oneMonth -> {
            "${difference / oneDay }days"
        }
        difference <  oneYear -> {
            "${difference / oneMonth }months"
        }
        else -> {
            "${difference / oneYear }yrs"
        }
    }

    return context.getString(R.string.next_check_message, duration)
}