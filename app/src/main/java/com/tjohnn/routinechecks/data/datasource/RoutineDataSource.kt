package com.tjohnn.routinechecks.data.datasource

import com.tjohnn.routinechecks.data.model.Routine

interface RoutineDataSource {
    /**
     * Gets and returns all the routines added by user
     */
    fun getAllRoutines(): List<Routine>
}