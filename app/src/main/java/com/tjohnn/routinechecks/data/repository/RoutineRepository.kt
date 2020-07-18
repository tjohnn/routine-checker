package com.tjohnn.routinechecks.data.repository

import com.tjohnn.routinechecks.data.datasource.RoutineDataSource
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.utils.dispatcher.CoroutineDispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val routineDataSource: RoutineDataSource,
    private val coroutineDispatchers: CoroutineDispatchers
) {

    fun observeAllRoutines() = routineDataSource.observeAllRoutines()

    fun observeNextUpRoutines() = routineDataSource.observeNextUpRoutines()

    suspend fun saveRoutine(form: Routine) = withContext(coroutineDispatchers.io()) {
        routineDataSource.saveRoutine(form)
    }

    suspend fun getRoutineById(id: Int) = withContext(coroutineDispatchers.io()) {
        routineDataSource.getById(id)
    }

    fun observeRoutineById(id: Int) = routineDataSource.observeRoutineById(id)

    suspend fun getNextRoutineForReminder() = withContext(coroutineDispatchers.io()) {
        routineDataSource.getNextRoutineForReminder()
    }

    suspend fun getElapsedRoutines() = withContext(coroutineDispatchers.io()) {
        routineDataSource.getElapsedRoutines()
    }

    suspend fun updateAll(newList: List<Routine>) = withContext(coroutineDispatchers.io())  {
        routineDataSource.updateRoutines(newList)
    }


}