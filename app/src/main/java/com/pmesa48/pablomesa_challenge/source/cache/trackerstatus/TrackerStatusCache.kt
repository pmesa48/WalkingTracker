package com.pmesa48.pablomesa_challenge.source.cache.trackerstatus

import com.pmesa48.pablomesa_challenge.model.TrackerStatus
import kotlinx.coroutines.flow.Flow

interface TrackerStatusCache {

    fun get(id: Int): Flow<TrackerStatus>

    fun getSingle(id: Int): TrackerStatus?

    fun change(status: TrackerStatus)

    fun add(trackerStatus: TrackerStatus)
}
