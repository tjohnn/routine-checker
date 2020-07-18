package com.tjohnn.routinechecks.data.mapper

import com.tjohnn.routinechecks.data.local.RoutineEntity
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutineEntityToModelMapper @Inject constructor() : Mapper<RoutineEntity, Routine> {
    override fun map(from: RoutineEntity) = Routine(
            from.id,
            from.title,
            from.description,
            from.routineFrequency,
            RoutineDuration.fromValue(from.durationUnit),
            Date(from.nextCheckTime),
            from.numberOfMissed,
            from.numberOfDone,
            from.isPending,
            Date(from.previousCheckTime)
    )
}