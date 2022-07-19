package com.pmesa48.pablomesa_challenge.model.repository

import com.pmesa48.pablomesa_challenge.source.cache.trackerstatus.TrackerStatusCache
import kotlinx.coroutines.flow.onStart

class TrackerStatusRepository(
    private val trackerStatusCache: TrackerStatusCache
) {

    fun get(id: Int) = trackerStatusCache
        .getAsFlow(id)
        .onStart {
            if (trackerStatusCache.get(id) == null)
                change(id, false)
            }

    fun change(id: Int, active: Boolean) { trackerStatusCache.change(id, active) }
}