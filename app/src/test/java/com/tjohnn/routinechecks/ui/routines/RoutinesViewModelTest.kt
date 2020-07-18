package com.tjohnn.routinechecks.ui.routines

import com.tjohnn.routinechecks.FakeRoutineDataSource
import com.tjohnn.routinechecks.MainCoroutineRule
import com.tjohnn.routinechecks.TestCoroutineDispatchers
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import com.tjohnn.routinechecks.data.repository.RoutineRepository
import com.tjohnn.routinechecks.ui.EmptyData
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RoutinesViewModelTest {


    private val createRoutine1 = Routine (
        id = 0, title = "Routine 1", description = "Description 1", frequency = 1, duration = RoutineDuration.HOURLY
    )
    private val createRoutine2 = Routine (
        id = 0, title = "Routine 2", description = "Description 2", frequency = 1, duration = RoutineDuration.DAILY
    )
    private val routine1 = Routine (
        id = 1, title = "Routine 1", description = "Description 1", frequency = 1, duration = RoutineDuration.HOURLY
    )
    private val routine2 = Routine (
        id = 2, title = "Routine 2", description = "Description 2", frequency = 1, duration = RoutineDuration.DAILY
    )

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repo: RoutineRepository
    private lateinit var viewModel: RoutinesViewModel

    @Before
    fun setUp() {
        repo = RoutineRepository(FakeRoutineDataSource(), TestCoroutineDispatchers())
        viewModel = RoutinesViewModel(repo)
    }

    @Test
    fun observeAllRoutines_emitsAllRoutinesToUi() = runBlockingTest {
        repo.saveRoutine(createRoutine1)
        repo.saveRoutine(createRoutine2)

        viewModel.observeRoutines()

        assertEquals(listOf(routine1, routine2), viewModel.routines.value)
    }

    @Test
    fun observeAllRoutines_emitsEmpty() = runBlockingTest {
        viewModel.observeRoutines()

        assertEquals(listOf<Routine>(), viewModel.routines.value)
        assertTrue(viewModel.screenState.value is EmptyData)
    }
}