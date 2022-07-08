package com.pmesa48.pablomesa_challenge.source.cache.entries

import com.pmesa48.pablomesa_challenge.model.Entry
import kotlinx.coroutines.flow.Flow

interface EntryCache {
    fun add(entry: Entry)

    fun getAll(): Flow<List<Entry>>

    suspend fun getLast(): Entry?

    fun nuke()
}
