package com.pmesa48.pablomesa_challenge.domain.usecase.endactivity

import com.pmesa48.pablomesa_challenge.model.repository.EntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EndActivityUseCaseImpl(
    private val entryRepository: EntryRepository
) : EndActivityUseCase {

    override suspend fun end() {
        withContext(Dispatchers.IO) { entryRepository.nuke() }
    }
}