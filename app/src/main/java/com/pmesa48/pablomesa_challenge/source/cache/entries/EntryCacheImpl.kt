package com.pmesa48.pablomesa_challenge.source.cache.entries

import com.pmesa48.pablomesa_challenge.model.Entry

class EntryCacheImpl(
    private val entryDao: EntryDao
) : EntryCache {
    override fun add(entry: Entry) { entryDao.add(entry) }

    override fun getAll() = entryDao.getAllAsFlow()

    override suspend fun getLast() = entryDao.latest()

    override fun nuke() { entryDao.deleteAll() }
}