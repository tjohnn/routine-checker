package com.tjohnn.routinechecks

import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.BuildConfig
import androidx.work.Configuration
import androidx.work.WorkManager
import com.tjohnn.routinechecks.di.DaggerAppComponent
import com.tjohnn.routinechecks.di.workermodule.RoutineWorkerFactory
import com.tjohnn.routinechecks.utils.TimberTree
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject


class App : DaggerApplication() {

    private lateinit var injector: AndroidInjector<out DaggerApplication>
    @Inject
    lateinit var workerFactory: RoutineWorkerFactory

    override fun onCreate() {

        injector = DaggerAppComponent.builder().application(this).build()

        super.onCreate()

        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())

        if (BuildConfig.DEBUG) {
            Timber.plant(TimberTree())
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    }

    override fun applicationInjector() = injector
}