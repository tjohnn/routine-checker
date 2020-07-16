package com.tjohnn.routinechecks.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
interface AppModule {
    @Binds
    fun provideApplication(application: Application): Context
}