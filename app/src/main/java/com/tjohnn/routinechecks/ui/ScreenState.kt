package com.tjohnn.routinechecks.ui


sealed class ScreenState
data class LoadError(val message: String): ScreenState()
data class EmptyData(
    val message: String
): ScreenState()
object Idle : ScreenState()