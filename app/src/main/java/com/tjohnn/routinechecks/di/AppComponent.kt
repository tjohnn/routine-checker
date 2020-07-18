package com.tjohnn.routinechecks.di

import android.app.Application
import com.tjohnn.routinechecks.App
import com.tjohnn.routinechecks.di.viewmodelmodule.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class, ActivityBindingModule::class,
    AndroidSupportInjectionModule::class, ViewModelModule::class,
    RoomModule::class, ServiceBindingModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}