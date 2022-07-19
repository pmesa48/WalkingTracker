package com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers

import com.pmesa48.pablomesa_challenge.domain.usecase.common.mappers.DataMapper
import com.pmesa48.pablomesa_challenge.domain.usecase.common.exceptions.NotEnoughParametersException
import com.pmesa48.pablomesa_challenge.model.Location
import com.pmesa48.pablomesa_challenge.source.dto.UserLocationDto
import java.util.*

class LocationMapper : DataMapper<UserLocationDto, Location> {

    override fun map(value: UserLocationDto): Location {
        if(value.lat == null || value.lng == null || value.date == null)
            throw NotEnoughParametersException("Missing parameters in object: $value")
        return Location(
            value.lat,
            value.lat,
            Date(value.date)
        )
    }
}