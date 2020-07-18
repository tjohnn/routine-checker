package com.tjohnn.routinechecks.data.local

import androidx.room.*
import com.tjohnn.routinechecks.data.model.Routine
import kotlinx.coroutines.flow.Flow


@Dao
abstract class RoutinesDao {

    @Query("SELECT * FROM routines")
    abstract fun observeAllRoutines(): Flow<List<RoutineEntity>>

    @Query("SELECT * FROM routines where nextCheckTime <= :time OR isPending == 1")
    abstract fun observeNextUpRoutines(time: Long): Flow<List<RoutineEntity>>

    @Query("SELECT * FROM routines where previousCheckTime <= :time  AND isPending = 1")
    abstract suspend fun getElapsedRoutines(time: Long): List<RoutineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(routine: RoutineEntity): Long

    @Query("SELECT * FROM routines where id = :routineId")
    abstract suspend fun getById(routineId: Int): RoutineEntity

    @Query("SELECT * FROM routines where id = :routineId")
    abstract fun observeRoutineById(routineId: Int): Flow<RoutineEntity?>

    @Query("""SELECT * FROM routines where nextCheckTime = (
                    SELECT MIN(nextCheckTime) FROM routines WHERE 
                    nextCheckTime > :currentTime)""")
    abstract suspend fun getNextRoutine(currentTime: Long): RoutineEntity?

    @Update
    abstract fun updateAll(map: List<RoutineEntity>)


}