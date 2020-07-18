package com.tjohnn.routinechecks.data.datasource

import com.tjohnn.routinechecks.data.Result
import com.tjohnn.routinechecks.data.local.RoutinesDao
import com.tjohnn.routinechecks.data.mapper.RoutineEntityToModelMapper
import com.tjohnn.routinechecks.data.mapper.RoutineModelToEntityMapper
import com.tjohnn.routinechecks.data.model.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class RoomRoutineDataSource @Inject constructor(
    private val routinesDao: RoutinesDao,
    private val entityMapper: RoutineEntityToModelMapper,
    private val modelMapper: RoutineModelToEntityMapper
) : RoutineDataSource {

    override fun observeAllRoutines(): Flow<List<Routine>> {
        return routinesDao.observeAllRoutines()
            .mapLatest { list -> list.map { entityMapper.map(it) } }
    }

    override fun observeNextUpRoutines(): Flow<List<Routine>> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, 12)
        return routinesDao.observeNextUpRoutines(calendar.timeInMillis)
            .mapLatest { list -> list.map { entityMapper.map(it) } }
    }

    override fun observeRoutineById(id: Int): Flow<Routine?> {
        return routinesDao.observeRoutineById(id)
            .mapLatest {
                if(it != null) {
                    entityMapper.map(it)
                } else {
                    null
                }
            }
    }

    override suspend fun saveRoutine(routine: Routine): Result<Long> {
        return try {
            val id = routinesDao.save(modelMapper.map(routine))
            Result.Success(id)
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

    override suspend fun getById(id: Int): Result<Routine> {
        return try {
            val routine = entityMapper.map(routinesDao.getById(id))
            Result.Success(routine)
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

    override suspend fun getElapsedRoutines(): List<Routine> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, -5)
        return routinesDao.getElapsedRoutines(calendar.timeInMillis)
                .map { entityMapper.map(it) }
    }

    override suspend fun updateRoutines(newList: List<Routine>): Result<Unit> {
        return try {
            routinesDao.updateAll(newList.map { modelMapper.map(it) })
            Result.Success(Unit)
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

    override suspend fun getNextRoutineForReminder(): Routine? {
        return try {
            val currentTime = Date().time
            routinesDao.getNextRoutine(currentTime)?.let { entityMapper.map(it) }
        } catch (ex: Exception) {
            null
        }
    }

}