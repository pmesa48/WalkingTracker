package com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers

import com.pmesa48.pablomesa_challenge.domain.usecase.common.mappers.DataMapper
import com.pmesa48.pablomesa_challenge.domain.usecase.common.exceptions.NotEnoughParametersException
import com.pmesa48.pablomesa_challenge.model.Photo
import com.pmesa48.pablomesa_challenge.source.dto.PhotoDto

class PhotoMapper : DataMapper<PhotoDto, Photo> {
    override fun map(value: PhotoDto): Photo {
        if(value.id == null || value.secret == null || value.serverId == null)
            throw NotEnoughParametersException("Missing parameters in object: $value")
        return Photo(
            value.id,
            value.isPublic ?: false,
            value.getUrl()
        )
    }

}
