package com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations

import android.location.Location
import com.pmesa48.pablomesa_challenge.model.Entry
import com.pmesa48.pablomesa_challenge.model.repository.EntryRepository
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource.Result.Error
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource.Result.Success
import com.pmesa48.pablomesa_challenge.source.device.LocationSource
import com.pmesa48.pablomesa_challenge.source.dto.UserLocationDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class FilterLocationByThresholdUseCaseImpl constructor(
    private val locationSource: LocationSource,
    private val photoSource: PhotoSource,
    private val entryRepository: EntryRepository
) : FilterLocationByThresholdUseCase {

    override fun get(distanceInMeters: Int) =
        locationSource.start()
            .flowOn(Dispatchers.IO)
            .filter { byThreshold(entryRepository.getLastEntry(), it, distanceInMeters) }
            .map { getPhoto(it) }
            .filter { it.imagePath.isNotEmpty() }
            .onEach { entryRepository.add(it) }

    private suspend fun getPhoto(it: UserLocationDto) =
        when (val result = photoSource.getPhotoByLocation(it.lat, it.lng)) {
            is Success -> it.toDomain(result.photos.filter { it.isPublic }.random().getUrl())
            is Error -> it.toDomain("")
        }

    private fun byThreshold(
        lastEntry: Entry?,
        newLocation: UserLocationDto,
        distanceInMeters: Int
    ): Boolean {
        val results = FloatArray(1)
        if (lastEntry == null)
            return true
        Location.distanceBetween(
            lastEntry.latitude, lastEntry.longitude,
            newLocation.lat, newLocation.lng, results
        )
        return results[0].toInt() >= distanceInMeters
    }
}

private fun UserLocationDto.toDomain(imagePath: String) =
    Entry(
        latitude = lat,
        longitude = lng,
        date = date,
        imagePath = imagePath
    )

