package com.tjohnn.routinechecks.data.model

data class Routine (
    val id: Long,
    val title: String,
    val description: String,
    val nextCheckTime: Long,
    val numberOfMissed: Int,
    val numberOfDone: Int,
    val frequency: Int,
    val duration: RoutineDuration
)

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