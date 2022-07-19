package com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers

import com.pmesa48.pablomesa_challenge.domain.usecase.common.mappers.PayloadDataMapper
import com.pmesa48.pablomesa_challenge.model.Location
import com.pmesa48.pablomesa_challenge.model.Photo
import com.pmesa48.pablomesa_challenge.model.Entry

class EntryMapper : PayloadDataMapper<Location, Photo, Entry> {

    override fun map(value: Location, payload: Photo?) =
        Entry(value.latitude, value.longitude, value.date.time, payload?.url ?: "")
}