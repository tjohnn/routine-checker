package com.tjohnn.routinechecks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
abstract class RoutinesDao {

    @Query("SELECT * FROM routines")
    abstract fun getRoutines(): Flow<List<RoutineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(store: RoutineEntity)


}