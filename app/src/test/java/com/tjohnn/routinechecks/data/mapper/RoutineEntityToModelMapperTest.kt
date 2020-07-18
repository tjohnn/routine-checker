package com.tjohnn.routinechecks.data.mapper

import com.tjohnn.routinechecks.data.local.RoutineEntity
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class RoutineEntityToModelMapperTest {

    @Test
    fun map_mapsCorrectly() {
        val checkTime = Date()
        val from = RoutineEntity (
            id = 1, title = "Routine 1", description = "Description 1",
            routineFrequency = 1, durationUnit = RoutineDuration.HOURLY.durationValue,
            isPending = false, numberOfMissed = 0, numberOfDone = 0, nextCheckTime = checkTime.time,
            previousCheckTime = checkTime.time
        )

        val to = Routine (
            id = 1, title = "Routine 1", description = "Description 1", frequency = 1, duration = RoutineDuration.HOURLY,
            isPending = false, numberOfMissed = 0, numberOfDone = 0, nextCheckTime = checkTime,
            previousCheckTime = checkTime
        )
        val mapper = RoutineEntityToModelMapper()
        assertEquals(to, mapper.map(from))
    }
}