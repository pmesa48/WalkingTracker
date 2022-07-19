package com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations

import com.pmesa48.pablomesa_challenge.model.Entry
import com.pmesa48.pablomesa_challenge.model.repository.EntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class FilterLocationByThresholdUseCaseImpl constructor(
    private val dataManager: FilterLocationByThresholdManager,
    private val entryRepository: EntryRepository
) : FilterLocationByThresholdUseCase {

    override fun get(distanceInMeters: Int) : Flow<Entry> =
        dataManager.start()
            .flowOn(Dispatchers.IO)
            .filter { dataManager.byThreshold(entryRepository.getLastEntry(), it, distanceInMeters) }
            .map { location ->  dataManager.getPhoto(location) }
            .onEach { entry -> if(entry.hasImage()) entryRepository.add(entry) }
}

