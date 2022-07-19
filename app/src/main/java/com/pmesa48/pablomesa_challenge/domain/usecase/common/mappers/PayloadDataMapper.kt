package com.pmesa48.pablomesa_challenge.domain.usecase.common.mappers

import com.pmesa48.pablomesa_challenge.domain.usecase.common.exceptions.NotEnoughParametersException

interface PayloadDataMapper<in T, in P, out K> {

    @Throws(NotEnoughParametersException::class)
    fun map(value: T, payload: P?) : K
}