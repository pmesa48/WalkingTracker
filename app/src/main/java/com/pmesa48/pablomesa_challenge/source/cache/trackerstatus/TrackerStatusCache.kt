package com.pmesa48.pablomesa_challenge.source.cache.trackerstatus

import com.pmesa48.pablomesa_challenge.model.TrackerStatus
import kotlinx.coroutines.flow.Flow

interface TrackerStatusCache {

    fun getAsFlow(id: Int): Flow<TrackerStatus>

    fun get(id: Int): TrackerStatus?

    fun change(id: Int, active: Boolean)
}
