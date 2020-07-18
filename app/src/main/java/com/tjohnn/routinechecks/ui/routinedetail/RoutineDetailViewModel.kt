package com.tjohnn.routinechecks.ui.routinedetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tjohnn.routinechecks.data.Result
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.repository.RoutineRepository
import com.tjohnn.routinechecks.ui.Idle
import com.tjohnn.routinechecks.ui.LoadError
import com.tjohnn.routinechecks.ui.ScreenState
import com.tjohnn.routinechecks.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class RoutineDetailViewModel @Inject constructor(
    private val routineRepository: RoutineRepository
) : ViewModel() {

    private val _routine = MutableStateFlow<Routine?>(null)
    val routine = _routine as StateFlow<Routine?>

    private val _snackbarMessage = MutableStateFlow<Event<String>?>(null)
    val snackbarMessage = _snackbarMessage as StateFlow<Event<String>?>

    private val _screenState = MutableStateFlow<ScreenState>(Idle)
    val screenState = _screenState as StateFlow<ScreenState>

    fun getRoutineDetails(id: Int) {
        viewModelScope.launch {
            routineRepository.observeRoutineById(id)
                .catch {
                    _screenState.value = LoadError(it.message ?: "Error: $it")
                }.collect {
                    if (it != null) {
                        _routine.value = it
                        updatePendingIfNeeded(it)
                    } else {
                        _screenState.value = LoadError("Routine not found")
                    }
                }
        }
    }

    private fun updatePendingIfNeeded(routine: Routine) {
        val cal = Calendar.getInstance()
        cal.time = routine.previousCheckTime
        cal.add(Calendar.MINUTE, 5)
        println("Update pending status")
        println("$routine")
        if(routine.isPending && cal.before(Calendar.getInstance())) {
            println("Updateing pending status")
            // state flow will not emit value except a new instance is created
            val newVal = routine.copy(isPending = false, numberOfMissed = routine.numberOfMissed+1)
            println("$newVal")
            updateRoutine(newVal)
        }
    }

    fun markAsDone(){
        val routine = _routine.value ?: return
        // stateflow will not emit value except a new instance is created
        val newVal = routine.copy(isPending = false, numberOfDone = routine.numberOfDone+1)
        updateRoutine(newVal)
    }

    private fun updateRoutine(routine: Routine) {
        viewModelScope.launch {
            when (val result = routineRepository.saveRoutine(routine)) {
                is Result.Success -> {}
                is Result.Error -> {
                    val message = result.exception.message ?: "Unable to update"
                    _snackbarMessage.value = Event(message)
                }
            }

        }
    }

}