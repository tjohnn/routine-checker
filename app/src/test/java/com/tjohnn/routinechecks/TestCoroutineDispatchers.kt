package com.tjohnn.routinechecks

import com.tjohnn.routinechecks.utils.dispatcher.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers

class TestCoroutineDispatchers : CoroutineDispatchers {
    override fun io() = Dispatchers.Main

    override fun main() = Dispatchers.Main

    override fun computation() = Dispatchers.Main
}