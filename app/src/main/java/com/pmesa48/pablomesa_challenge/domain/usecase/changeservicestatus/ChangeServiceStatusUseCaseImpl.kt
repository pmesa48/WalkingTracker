package com.pmesa48.pablomesa_challenge.domain.usecase.changeservicestatus

import com.pmesa48.pablomesa_challenge.model.repository.TrackerStatusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ChangeServiceStatusUseCaseImpl(
    private val trackerStatusRepository: TrackerStatusRepository
) : ChangeServiceStatusUseCase {

    companion object {
        private const val SERVICE_ID = 57
    }

    override suspend fun changeState(active: Boolean) {
        withContext(Dispatchers.IO) {
            trackerStatusRepository.change(SERVICE_ID, active)
        }
    }

    override fun getState() = trackerStatusRepository.get(SERVICE_ID).flowOn(Dispatchers.IO)
}