package com.tjohnn.routinechecks.di

import com.tjohnn.routinechecks.service.BackgroundAlarmSchedulerService
import com.tjohnn.routinechecks.service.ForegroundAlarmSchedulerService
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface ServiceBindingModule {

    @ContributesAndroidInjector
    fun backgroundUpdateService(): BackgroundAlarmSchedulerService

    @ContributesAndroidInjector
    fun foregroundUpdateService(): ForegroundAlarmSchedulerService


}