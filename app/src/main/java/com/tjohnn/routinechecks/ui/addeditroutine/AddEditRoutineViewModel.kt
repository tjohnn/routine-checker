package com.tjohnn.routinechecks.ui.addeditroutine

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.data.Result
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import com.tjohnn.routinechecks.data.repository.RoutineRepository
import com.tjohnn.routinechecks.ui.Idle
import com.tjohnn.routinechecks.ui.LoadError
import com.tjohnn.routinechecks.ui.ScreenState
import com.tjohnn.routinechecks.utils.Event
import com.tjohnn.routinechecks.utils.computeNextRoutineTimeFromNow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AddEditRoutineViewModel @Inject constructor(
    private val app: Application,
    private val routineRepository: RoutineRepository
) : ViewModel() {

    private val _routine = MutableStateFlow<Routine?>(null)
    val routine = _routine as StateFlow<Routine?>

    private val _onRoutineSaved = MutableStateFlow(Event(false))
    val onRoutineSaved = _onRoutineSaved as StateFlow<Event<Boolean>>

    private val _formError = MutableStateFlow<String?>(null)
    val formError = _formError as StateFlow<String?>

    private val _frequencyDurations = MutableStateFlow<List<String>>(listOf())
    val frequencyDurations = _frequencyDurations as StateFlow<List<String>>

    private val _screenState = MutableStateFlow<ScreenState>(Idle)
    val screenState = _screenState as StateFlow<ScreenState>

    init {
        _frequencyDurations.value = RoutineDuration.values().map { it.durationValue }
    }

    fun getRoutineDetails(id: Int) {
        viewModelScope.launch {
            when (val result = routineRepository.getRoutineById(id)) {
                is Result.Success -> {
                    _routine.value = result.data
                }
                is Result.Error -> {
                    val message = result.exception.message ?: "Unable to load details"
                    _screenState.value = LoadError(message)
                }
            }

        }
    }

    fun saveRoutine(form: Routine) {
        var errors = ""
        if (form.title.isBlank()) {
            errors += app.getString(R.string.title_is_required) + "\n"
        }
        if (form.description.isBlank()) {
            errors += app.getString(R.string.description_is_required) + "\n"
        }
        if (form.frequency == 0) {
            errors += app.getString(R.string.please_set_frequency) + "\n"
        }
        _formError.value = errors
        if(errors.isNotEmpty()) {
            return
        }

        form.nextCheckTime = computeNextRoutineTimeFromNow(form).time

        viewModelScope.launch {
            when (val result = routineRepository.saveRoutine(form)) {
                is Result.Success -> {
                    _onRoutineSaved.value = Event(true)
                }
                is Result.Error -> {
                    val message = result.exception.message ?: "Unable to load details"
                    _screenState.value = LoadError(message)
                }
            }

        }
    }

}