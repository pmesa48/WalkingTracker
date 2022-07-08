package com.pmesa48.pablomesa_challenge.source.cache.trackerstatus

import com.pmesa48.pablomesa_challenge.model.TrackerStatus
import kotlinx.coroutines.flow.Flow

class TrackerStatusCacheImpl(
    private val trackerStatusDao: TrackerStatusDao
) : TrackerStatusCache {

    override fun change(id: Int, active: Boolean) {
        trackerStatusDao.add(TrackerStatus(id, active))
    }

    override fun add(trackerStatus: TrackerStatus) {
        trackerStatusDao.add(trackerStatus)
    }

    override fun get(id: Int): Flow<TrackerStatus> = trackerStatusDao.get(id)

    override fun getSingle(id: Int): TrackerStatus? {
        return trackerStatusDao.getSingle(id)
    }
}