package com.tjohnn.routinechecks.di

import android.app.Application
import android.content.Context
import com.tjohnn.routinechecks.data.datasource.RoomRoutineDataSource
import com.tjohnn.routinechecks.data.datasource.RoutineDataSource
import com.tjohnn.routinechecks.utils.dispatcher.CoroutineDispatchers
import com.tjohnn.routinechecks.utils.dispatcher.DefaultCoroutineDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface AppModule {
    @Binds
    fun provideApplication(application: Application): Context

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideRoutineDataSource(dataSource: RoomRoutineDataSource): RoutineDataSource {
            return  dataSource
        }

        @JvmStatic
        @Provides
        fun provideCoroutineDispatcher(defaultCoroutineDispatchers: DefaultCoroutineDispatchers): CoroutineDispatchers {
            return  defaultCoroutineDispatchers
        }
    }
}