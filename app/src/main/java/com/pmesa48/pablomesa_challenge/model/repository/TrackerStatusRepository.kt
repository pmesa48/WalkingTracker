package com.pmesa48.pablomesa_challenge.model.repository

import com.pmesa48.pablomesa_challenge.model.TrackerStatus
import com.pmesa48.pablomesa_challenge.source.cache.trackerstatus.TrackerStatusCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class TrackerStatusRepository(
    private val trackerStatusCache: TrackerStatusCache
) {

    fun get(id: Int) =
        trackerStatusCache.get(id)
            .onStart {
                if (trackerStatusCache.getSingle(id) == null)
                    trackerStatusCache.add(TrackerStatus(id, false))
            }

    fun change(id: Int, active: Boolean) { trackerStatusCache.change(TrackerStatus(id, active)) }
}