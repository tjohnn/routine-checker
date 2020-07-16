package com.tjohnn.routinechecks.di.workermodule

import com.tjohnn.routinechecks.RoutineWorker
import com.tjohnn.routinechecks.di.annotations.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(RoutineWorker::class)
    fun bindFirebaseTokenScheduler(factory: RoutineWorker.Factory): ChildWorkerFactory
}