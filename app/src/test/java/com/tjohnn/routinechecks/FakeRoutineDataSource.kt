package com.tjohnn.routinechecks

import com.tjohnn.routinechecks.data.Result
import com.tjohnn.routinechecks.data.datasource.RoutineDataSource
import com.tjohnn.routinechecks.data.model.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.security.InvalidParameterException
import java.util.*

class FakeRoutineDataSource : RoutineDataSource {

    private val database: MutableList<Routine> = mutableListOf()


    override fun observeAllRoutines(): Flow<List<Routine>> {
        return flowOf(database)
    }

    fun getAll() = database.toList()

    override fun observeNextUpRoutines(): Flow<List<Routine>> {
        val cal = Calendar.getInstance()
        cal.add(Calendar.HOUR, 12)
        val nextUps = database.filter { it.nextCheckTime.before(cal.time) }
        return flowOf(nextUps)
    }

    override fun observeRoutineById(id: Int): Flow<Routine?> {
        return flowOf(database.firstOrNull { it.id == id })
    }

    override suspend fun saveRoutine(routine: Routine): Result<Long> {
        val existingIndex = database.indexOfFirst { it.id == routine.id }
        val id: Int
        if(existingIndex == -1) {
            id = database.size + 1
            database.add(routine.copy(id = id))
        } else {
            id = routine.id
            database[existingIndex] = routine
        }
        return Result.Success(id.toLong())
    }

    override suspend fun getById(id: Int): Result<Routine> {
        val routine = database.firstOrNull { it.id == id }
            ?: return Result.Error(InvalidParameterException("Routine does not exist"))
        return Result.Success(routine)
    }

    override suspend fun getElapsedRoutines(): List<Routine> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, -5)
        return database.filter { it.nextCheckTime.before(calendar.time) }
    }

    override suspend fun updateRoutines(newList: List<Routine>): Result<Unit> {
        for(routine in newList) {
            val existingIndex = database.indexOfFirst { it.id == routine.id }
            if(existingIndex == -1) {
                database.add(routine)
            } else {
                database[existingIndex] = routine
            }
        }
        return Result.Success(Unit)
    }

    override suspend fun getNextRoutineForReminder(): Routine? {
        return database
            .filter { it.nextCheckTime.after(Date()) }
            .minBy { it.nextCheckTime }
    }


}