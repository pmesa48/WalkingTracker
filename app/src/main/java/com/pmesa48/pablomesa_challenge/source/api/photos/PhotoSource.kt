package com.pmesa48.pablomesa_challenge.source.api.photos

import com.pmesa48.pablomesa_challenge.source.dto.PhotoDto

interface PhotoSource {

    suspend fun getPhotoByLocation(latitude: Double, longitude: Double): Result

    sealed class Result {
        data class Error(val message: String) : Result()
        data class Success(val photos: List<PhotoDto>) : Result()
    }

    companion object {
        const val STATUS_OK = "ok"
    }
}