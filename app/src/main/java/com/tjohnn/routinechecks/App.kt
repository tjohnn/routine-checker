package com.tjohnn.routinechecks

import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Configuration
import com.tjohnn.routinechecks.di.DaggerAppComponent
import com.tjohnn.routinechecks.utils.TimberTree
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject


class App : DaggerApplication() {

    private lateinit var injector: AndroidInjector<out DaggerApplication>

    override fun onCreate() {

        injector = DaggerAppComponent.builder().application(this).build()

        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(TimberTree())
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    }

    override fun applicationInjector() = injector
}