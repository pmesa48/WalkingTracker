package com.pmesa48.pablomesa_challenge.domain.usecase.getentries

import com.pmesa48.pablomesa_challenge.model.Entry
import kotlinx.coroutines.flow.Flow

interface GetEntriesUseCase {
    fun get(): Flow<List<Entry>>
}