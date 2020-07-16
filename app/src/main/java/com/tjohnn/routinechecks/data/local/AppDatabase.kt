package com.tjohnn.routinechecks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoutineEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun routineDao(): RoutinesDao

    companion object {
        const val DATABASE_NAME = "routines"
    }
}