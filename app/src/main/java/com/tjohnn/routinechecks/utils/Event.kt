package com.tjohnn.routinechecks.utils

class Event<T>(private val content: T) {
    private var contentUsed = false
    val contentIfNotUsed: T?
        get() {
            if (!contentUsed) {
                contentUsed = true
                return content
            }
            return null
        }

    fun peekContent(): T {
        return content
    }

}