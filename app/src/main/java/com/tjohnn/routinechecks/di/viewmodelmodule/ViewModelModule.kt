package com.tjohnn.routinechecks.di.viewmodelmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.tjohnn.routinechecks.di.annotations.ViewModelKey
import com.tjohnn.routinechecks.ui.addeditroutine.AddEditRoutineViewModel
import com.tjohnn.routinechecks.ui.nextup.NextUpViewModel
import com.tjohnn.routinechecks.ui.routinedetail.RoutineDetailViewModel
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
    @IntoMap
    @ViewModelKey(AddEditRoutineViewModel::class)
    abstract fun provideAddEditRoutineViewModel(authViewModel: AddEditRoutineViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoutineDetailViewModel::class)
    abstract fun provideRoutineDetailViewModel(authViewModel: RoutineDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NextUpViewModel::class)
    abstract fun provideNextUpViewModel(authViewModel: NextUpViewModel): ViewModel

    @Binds
    abstract fun provideFactory(viewModelFactory: ViewModelFactory): Factory
}