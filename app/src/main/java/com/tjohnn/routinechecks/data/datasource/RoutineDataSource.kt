package com.tjohnn.routinechecks.data.datasource

import com.tjohnn.routinechecks.data.Result
import com.tjohnn.routinechecks.data.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineDataSource {
    /**
     * Observes and returns a [Flow] of all the routines added by user
     */
    fun observeAllRoutines(): Flow<List<Routine>>

    /**
     * Observes and returns a [Flow] of all the routines within the next 12hrs
     */
    fun observeNextUpRoutines(): Flow<List<Routine>>

    /**
     * Observes and returns a [Flow] of a routine whose id is given
     */
    fun observeRoutineById(id: Int): Flow<Routine?>

    /**
     * Adds or update a routine
     *
     * @param [routine] The routine model to save
     */
    suspend fun saveRoutine(routine: Routine): Result<Long>

    /**
     * Gets routine by id
     *
     * @param [id] The id of the routine to get
     */
    suspend fun getById(id: Int): Result<Routine>

    suspend fun getElapsedRoutines(): List<Routine>

    suspend fun updateRoutines(newList: List<Routine>): Result<Unit>

    suspend fun getNextRoutineForReminder(): Routine?
}