package com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations

import com.pmesa48.pablomesa_challenge.model.Entry
import kotlinx.coroutines.flow.Flow

interface FilterLocationByThresholdUseCase {
    fun get(distanceInMeters: Int = DISTANCE_IN_M): Flow<Entry>

    companion object {
        private const val DISTANCE_IN_M = 100
    }
}
