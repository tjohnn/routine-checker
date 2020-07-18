package com.tjohnn.routinechecks.ui.nextup

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.tjohnn.routinechecks.base.BaseViewModel
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.repository.RoutineRepository
import com.tjohnn.routinechecks.ui.LoadError
import com.tjohnn.routinechecks.ui.Idle
import com.tjohnn.routinechecks.ui.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NextUpViewModel @Inject constructor(
        app: Application,
        private val routineRepository: RoutineRepository
) : BaseViewModel(app) {

    private val _screenState = MutableStateFlow<ScreenState>(Idle)
    val screenState = _screenState as StateFlow<ScreenState>

    private val _routines = MutableStateFlow<List<Routine>>(listOf())
    val routines = _routines as StateFlow<List<Routine>>

    init {
        observeRoutines()
    }

    private fun observeRoutines() {
        viewModelScope.launch {
            routineRepository.observeNextUpRoutines()
                    .catch {
                        _screenState.value = LoadError(it.message ?: "Error: $it")
                    }.collect {
                        _routines.value = it
                    }
        }
    }

}