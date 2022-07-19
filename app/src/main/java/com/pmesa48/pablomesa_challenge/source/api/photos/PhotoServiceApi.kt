package com.pmesa48.pablomesa_challenge.source.api.photos

import com.pmesa48.pablomesa_challenge.source.dto.GetPhotoByLocationDto
import retrofit2.http.GET
import retrofit2.http.Query


interface PhotoServiceApi {

    @GET("?method=flickr.photos.search")
    suspend fun getPhotosByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): GetPhotoByLocationDto?
}