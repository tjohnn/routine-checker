package com.tjohnn.routinechecks

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tjohnn.routinechecks.data.repository.RoutineRepository
import com.tjohnn.routinechecks.di.workermodule.ChildWorkerFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class RoutineWorker @Inject constructor(
    context: Context, workerParams: WorkerParameters,
    private val routineRepository: RoutineRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        TODO()
    }

    class Factory @Inject constructor(
        private val repoProvider: Provider<RoutineRepository>
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return RoutineWorker(
                appContext,
                params,
                repoProvider.get()
            )
        }

    }

    companion object {
        private const val WORK_SCHEDULE_TOKEN = "schedule_token"

    }
}