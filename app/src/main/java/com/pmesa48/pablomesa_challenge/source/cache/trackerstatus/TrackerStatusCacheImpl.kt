package com.pmesa48.pablomesa_challenge.source.cache.trackerstatus

import com.pmesa48.pablomesa_challenge.model.TrackerStatus
import kotlinx.coroutines.flow.Flow

class TrackerStatusCacheImpl(
    private val trackerStatusDao: TrackerStatusDao
) : TrackerStatusCache {

    override fun getAsFlow(id: Int) = trackerStatusDao.getAsFlow(id)

    override fun get(id: Int) = trackerStatusDao.get(id)

    override fun change(id: Int, active: Boolean) {
        trackerStatusDao.add(TrackerStatus(id, active))
    }
}