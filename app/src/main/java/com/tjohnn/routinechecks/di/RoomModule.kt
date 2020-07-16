package com.tjohnn.routinechecks.di

import android.app.Application
import androidx.room.Room
import com.tjohnn.routinechecks.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun appDatabase(context: Application) = Room
        .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun storeDao(appDatabase: AppDatabase) = appDatabase.routineDao()

}