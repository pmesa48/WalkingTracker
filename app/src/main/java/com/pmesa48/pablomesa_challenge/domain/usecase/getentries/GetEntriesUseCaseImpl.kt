package com.pmesa48.pablomesa_challenge.domain.usecase.getentries

import com.pmesa48.pablomesa_challenge.model.repository.EntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class GetEntriesUseCaseImpl(
    private val entryRepository: EntryRepository
) : GetEntriesUseCase {

    override fun get() = entryRepository.getAll().flowOn(Dispatchers.IO)
}