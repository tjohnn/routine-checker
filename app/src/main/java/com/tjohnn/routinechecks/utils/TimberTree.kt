package com.tjohnn.routinechecks.utils

import timber.log.Timber.DebugTree

class TimberTree : DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format(
            "C:%s:%s",
            super.createStackElementTag(element),
            element.lineNumber
        )
    }
}