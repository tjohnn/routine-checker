package com.tjohnn.routinechecks.data.datasource

import com.tjohnn.routinechecks.data.local.RoutineEntity
import com.tjohnn.routinechecks.data.model.Routine
import javax.inject.Inject

class RoomRoutineDataSource @Inject constructor(
    private val routineEntity: RoutineEntity
) : RoutineDataSource {

    override fun getAllRoutines(): List<Routine> {
        TODO()
    }

}