package com.tjohnn.routinechecks.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

// TODO: Make all properties immutable
@Parcelize
data class Routine (
    val id: Int,
    val title: String,
    val description: String,
    val frequency: Int,
    val duration: RoutineDuration,
    var nextCheckTime: Date = Date(),
    var numberOfMissed: Int = 0,
    var numberOfDone: Int = 0,
    var isPending: Boolean = false,
    var previousCheckTime: Date = Date()
) : Parcelable

enum class RoutineDuration(val durationValue: String) {
    HOURLY("Hourly"),
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly");

    companion object {
        private val values by lazy { values() }
        fun fromValue(value: String) = values.first { it.durationValue == value }
    }
}