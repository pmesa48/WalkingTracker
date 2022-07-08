package com.pmesa48.pablomesa_challenge.domain.usecase.changeservicestatus

import com.pmesa48.pablomesa_challenge.model.TrackerStatus
import kotlinx.coroutines.flow.Flow

interface ChangeServiceStatusUseCase {

    suspend fun changeState(active: Boolean)

    fun getState(): Flow<TrackerStatus?>
}
