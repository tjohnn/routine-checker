package com.tjohnn.routinechecks.ui.routinedetail

import com.tjohnn.routinechecks.FakeRoutineDataSource
import com.tjohnn.routinechecks.MainCoroutineRule
import com.tjohnn.routinechecks.TestCoroutineDispatchers
import com.tjohnn.routinechecks.data.Result
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import com.tjohnn.routinechecks.data.repository.RoutineRepository
import com.tjohnn.routinechecks.ui.LoadError
import com.tjohnn.routinechecks.ui.routines.RoutinesViewModel
import com.tjohnn.routinechecks.utils.computeNextRoutineTimeFromLast
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.util.*

class RoutineDetailViewModelTest {

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
    private lateinit var viewModel: RoutineDetailViewModel

    @Before
    fun setUp() {
        repo = RoutineRepository(FakeRoutineDataSource(), TestCoroutineDispatchers())
        viewModel = RoutineDetailViewModel(repo)
    }

    @Test
    fun getRoutineDetails_success() = runBlockingTest{
        repo.saveRoutine(createRoutine1)
        repo.saveRoutine(createRoutine2)

        viewModel.getRoutineDetails(1)

        assertEquals(routine1, viewModel.routine.value)
    }

    @Test
    fun getRoutineDetails_notFound() = runBlockingTest{
        repo.saveRoutine(createRoutine1)
        repo.saveRoutine(createRoutine2)

        // try to get some non-existing id
        viewModel.getRoutineDetails(4)

        assertNull(viewModel.routine.value)
        assertTrue(viewModel.screenState.value is LoadError)
    }



    @Test
    fun getRoutine_updatesPendingStatus() = runBlockingTest {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, -7)
        val routine = createRoutine1.copy(isPending = true, previousCheckTime = cal.time)

        repo.saveRoutine(routine)
        repo.saveRoutine(createRoutine2)

        viewModel.getRoutineDetails(1)

        // verify that the `numberOfMissed` is incremented and `isPending` of routine is updated
        val newRoutine = repo.getRoutineById(1) as Result.Success

        assertFalse(newRoutine.data.isPending)
        assertEquals(newRoutine.data.numberOfMissed, routine.numberOfMissed + 1)
    }

    @Test
    fun markAsDone_updatesStatus() = runBlockingTest {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, -3)
        val routine = createRoutine1.copy(isPending = true, previousCheckTime = cal.time)

        repo.saveRoutine(routine)
        repo.saveRoutine(createRoutine2)

        // get routine details into `routine` value so `markAsDone()` can use it
        viewModel.getRoutineDetails(1)

        viewModel.markAsDone()

        // verify that the `numberOfDone` is incremented and `isPending` of routine is updated
        val newRoutine = repo.getRoutineById(1) as Result.Success

        assertFalse(newRoutine.data.isPending)
        assertEquals(newRoutine.data.numberOfDone, routine.numberOfDone + 1)
    }
}