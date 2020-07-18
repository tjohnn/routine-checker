package com.tjohnn.routinechecks.data.mapper

interface Mapper<in F, out T> {
    fun map(from: F): T
}