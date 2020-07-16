package com.tjohnn.routinechecks.di

import com.tjohnn.routinechecks.di.annotations.ActivityScoped
import com.tjohnn.routinechecks.di.annotations.ChildFragmentScoped
import com.tjohnn.routinechecks.di.annotations.FragmentScoped
import com.tjohnn.routinechecks.ui.MainActivity
import com.tjohnn.routinechecks.ui.MainFragment
import com.tjohnn.routinechecks.ui.addeditroutine.AddEditRoutineFragment
import com.tjohnn.routinechecks.ui.nextup.NextUpFragment
import com.tjohnn.routinechecks.ui.routines.RoutinesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun mainActivity(): MainActivity

}


@Module
interface MainActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    fun routineFragment(): MainFragment

    @FragmentScoped
    @ContributesAndroidInjector
    fun addEditRoutineFragment(): AddEditRoutineFragment

}

@Module
interface MainFragmentModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    fun routineFragment(): RoutinesFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector
    fun nextUpFragment(): NextUpFragment

}

