package com.pmesa48.pablomesa_challenge.source.device

import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.pmesa48.pablomesa_challenge.source.dto.UserLocationDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit

class FusedLocationSource(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationSource {

    private val locationRequest = LocationRequest.create().apply {
        interval = TimeUnit.SECONDS.toMillis(20)
        fastestInterval = TimeUnit.SECONDS.toMillis(10)
        maxWaitTime = TimeUnit.SECONDS.toMillis(10)
    }

    override fun start(): Flow<UserLocationDto> {
        return callbackFlow {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let { trySend(it.toSourceModel()) }
                }
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnFailureListener {
                close(it)
            }
            awaitClose {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }
    }

}


private fun Location.toSourceModel(): UserLocationDto {
    return UserLocationDto(
        lat = this.latitude,
        lng = this.longitude,
        date = System.currentTimeMillis()
    )
}
