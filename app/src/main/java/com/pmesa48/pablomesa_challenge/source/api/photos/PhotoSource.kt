package com.pmesa48.pablomesa_challenge.source.api.photos

import com.pmesa48.pablomesa_challenge.source.dto.PhotoDto

interface PhotoSource {

    suspend fun getPhotoByLocation(latitude: Double, longitude: Double): Result

    sealed class Result {
        data class Error(val code: Int, val message: String, val e: Throwable? = null) : Result()
        data class Success(val photos: List<PhotoDto>) : Result()
    }
}