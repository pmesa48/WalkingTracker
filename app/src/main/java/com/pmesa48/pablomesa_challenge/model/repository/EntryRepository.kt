package com.pmesa48.pablomesa_challenge.model.repository

import com.pmesa48.pablomesa_challenge.model.Entry
import com.pmesa48.pablomesa_challenge.source.cache.entries.EntryCache

class EntryRepository(
    private val entryCache: EntryCache
) {
    fun add(entry: Entry) {
        entryCache.add(entry)
    }

    fun getAll() = entryCache.getAll()

    suspend fun getLastEntry(): Entry? {
        return entryCache.getLast()
    }

    fun nuke() {
        entryCache.nuke()
    }

}
