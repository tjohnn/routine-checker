package com.tjohnn.routinechecks.di.viewmodelmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.tjohnn.routinechecks.di.annotations.ViewModelKey
import com.tjohnn.routinechecks.ui.routines.RoutinesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RoutinesViewModel::class)
    abstract fun provideRoutinesViewModel(authViewModel: RoutinesViewModel): ViewModel

    @Binds
    abstract fun provideFactory(viewModelFactory: ViewModelFactory): Factory
}