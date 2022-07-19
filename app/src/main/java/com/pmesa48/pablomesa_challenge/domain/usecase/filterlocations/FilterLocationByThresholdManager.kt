package com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations

import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers.EntryMapper
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers.LocationMapper
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers.PhotoMapper
import com.pmesa48.pablomesa_challenge.model.Entry
import com.pmesa48.pablomesa_challenge.model.Location
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource.Result.Error
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource.Result.Success
import com.pmesa48.pablomesa_challenge.source.device.LocationSource
import com.pmesa48.pablomesa_challenge.source.dto.PhotoDto
import kotlinx.coroutines.flow.map

class FilterLocationByThresholdManager constructor(
    private val photoSource: PhotoSource,
    private val locationSource: LocationSource,
    private val locationMapper: LocationMapper,
    private val photoMapper: PhotoMapper,
    private val entryMapper: EntryMapper
) {

    fun start() = locationSource
        .start()
        .map { locationMapper.map(it) }

    fun byThreshold(
        lastEntry: Entry?,
        newLocation: Location,
        distanceInMeters: Int
    ): Boolean {
        val results = FloatArray(1)
        if (lastEntry == null)
            return true
        android.location.Location.distanceBetween(
            lastEntry.latitude, lastEntry.longitude,
            newLocation.latitude, newLocation.longitude, results
        )
        return results[0].toInt() >= distanceInMeters
    }

     suspend fun getPhoto(location: Location): Entry {
         return when (val result =
             photoSource.getPhotoByLocation(location.latitude, location.longitude)) {
             is Success -> entryMapper.map(location, selectRandomPublicImage(result.photos))
             is Error -> Entry.empty()
         }
     }

    private fun selectRandomPublicImage(result: List<PhotoDto>) =
        result.map { photoMapper.map(it) }.filter { it.isPublic }.random()
}
