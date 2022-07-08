package com.pmesa48.pablomesa_challenge.source.device

import com.pmesa48.pablomesa_challenge.source.dto.UserLocationDto
import kotlinx.coroutines.flow.Flow

interface LocationSource {

    fun start(): Flow<UserLocationDto>
}
