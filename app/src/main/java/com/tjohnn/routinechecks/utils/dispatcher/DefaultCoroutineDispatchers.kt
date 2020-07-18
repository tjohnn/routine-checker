package com.tjohnn.routinechecks.utils.dispatcher

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCoroutineDispatchers @Inject constructor(): CoroutineDispatchers {
    override fun io() = Dispatchers.IO

    override fun main() = Dispatchers.Main

    override fun computation() = Dispatchers.Default

}