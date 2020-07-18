package com.tjohnn.routinechecks.data.mapper

import com.tjohnn.routinechecks.data.local.RoutineEntity
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutineModelToEntityMapper @Inject constructor() : Mapper<Routine, RoutineEntity> {
    override fun map(from: Routine) = RoutineEntity (
        from.id,
        from.title,
        from.description,
        from.nextCheckTime.time,
        from.numberOfMissed,
        from.numberOfDone,
        from.frequency,
        from.duration.durationValue,
        from.isPending,
        from.previousCheckTime.time
    )
}