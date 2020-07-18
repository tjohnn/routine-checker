package com.tjohnn.routinechecks.data.repository

import com.tjohnn.routinechecks.FakeRoutineDataSource
import com.tjohnn.routinechecks.MainCoroutineRule
import com.tjohnn.routinechecks.TestCoroutineDispatchers
import com.tjohnn.routinechecks.data.Result
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import com.tjohnn.routinechecks.utils.computeNextRoutineTimeFromNow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import org.junit.Assert.*

class RoutineRepositoryTest {

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

    lateinit var repo: RoutineRepository
    private val dataSource = FakeRoutineDataSource()

    @Before
    fun setUp() {
        val dispatchers = TestCoroutineDispatchers()
        repo = RoutineRepository(dataSource, dispatchers)
    }

    @Test
    fun saveRoutines_success() = runBlockingTest {
        val result = repo.saveRoutine(createRoutine1)

        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).data, 1L)
        assertEquals(dataSource.getAll().toString(), listOf(routine1).toString())
    }

    @Test
    fun updateRoutines_success() = runBlockingTest {
        repo.saveRoutine(createRoutine1)
        repo.saveRoutine(createRoutine2)

        val update = routine2.copy(title = "Title updated")
        repo.updateAll(listOf(update))

        assertEquals(dataSource.getAll().size, 2)
        assertEquals(dataSource.getAll().toString(), listOf(routine1, update).toString())
    }

    @Test
    fun observeRoutineById_success() = runBlockingTest {
        val result = (repo.saveRoutine(createRoutine1) as Result.Success).data

        dataSource.observeRoutineById(result.toInt()).collectLatest {
            assertEquals(it?.toString(), routine1.toString())
        }
    }

    @Test
    fun getRoutineById_success() = runBlockingTest {
        val result = (repo.saveRoutine(createRoutine1) as Result.Success).data

        val routineResult = dataSource.getById(result.toInt())

        assertTrue(routineResult is Result.Success)
        assertEquals((routineResult as Result.Success).data.toString(), routine1.toString())
    }

    @Test
    fun observeRoutineById_notFound() = runBlockingTest {
        repo.saveRoutine(createRoutine1) as Result.Success

        // query by a wrong id and assert that null is return
        dataSource.observeRoutineById(2).collectLatest {
            assertEquals(it, null)
        }
    }

    @Test
    fun observeAllRoutines_emitsEmptyList() = runBlockingTest {
        dataSource.observeAllRoutines().collectLatest {
            assertTrue(it.isEmpty())
        }
    }

    @Test
    fun observeAllRoutines_emitsAllData() = runBlockingTest {
        repo.saveRoutine(createRoutine1)
        repo.saveRoutine(createRoutine2)
        dataSource.observeAllRoutines().collectLatest {
            assertEquals(it.size, 2)
            assertEquals(it.toString(), listOf(routine1, routine2).toString())
        }
    }

    @Test
    fun observeNextUpRoutines_returnsRoutineWith12Hours() = runBlockingTest {
        // save routines with date
        val nextCheckTime = computeNextRoutineTimeFromNow(routine1).time
        repo.saveRoutine(createRoutine1.copy(nextCheckTime = nextCheckTime))
        repo.saveRoutine(createRoutine2.copy(nextCheckTime = computeNextRoutineTimeFromNow(createRoutine2).time))

        // next check time for routine2 would be 24hours, assert that only routine 1 gets returned
        dataSource.observeNextUpRoutines().collectLatest {
            val routine1Updated = routine1.copy(nextCheckTime = nextCheckTime)
            assertEquals(it.size, 1)
            assertEquals(it.toString(), listOf(routine1Updated).toString())
        }
    }

    @Test
    fun getElapsedRoutines() = runBlockingTest {
        // save routines with date
        val nextCheckTime = Calendar.getInstance()
        nextCheckTime.add(Calendar.MINUTE, -6)
        repo.saveRoutine(createRoutine1.copy(nextCheckTime = nextCheckTime.time))
        repo.saveRoutine(createRoutine2.copy(nextCheckTime = computeNextRoutineTimeFromNow(createRoutine2).time))

        // assert that elapsed routines ony contains routine1
        val routine1Updated = routine1.copy(nextCheckTime = nextCheckTime.time)
        val elapsedRoutines = dataSource.getElapsedRoutines()
        assertEquals(elapsedRoutines.size, 1)
        assertEquals(elapsedRoutines.toString(), listOf(routine1Updated).toString())
    }

    @Test
    fun getNextRoutineForReminder() = runBlockingTest {
        // save routines with date
        val nextCheckTime = computeNextRoutineTimeFromNow(createRoutine1)
        repo.saveRoutine(createRoutine1.copy(nextCheckTime = nextCheckTime.time))
        repo.saveRoutine(createRoutine2.copy(nextCheckTime = computeNextRoutineTimeFromNow(createRoutine2).time))

        // assert that elapsed routines ony contains routine1
        val routine1Updated = routine1.copy(nextCheckTime = nextCheckTime.time)
        val nextRoutine = dataSource.getNextRoutineForReminder()
        assertEquals(nextRoutine.toString(), routine1Updated.toString())
    }


}