package com.pmesa48.pablomesa_challenge.source.api.photos

import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource.Companion.STATUS_OK
import okio.IOException
import retrofit2.Retrofit
import java.net.UnknownHostException

class PhotoSourceImpl(
    retrofit: Retrofit
) : PhotoSource {

    val api: PhotoServiceApi = retrofit.create(PhotoServiceApi::class.java)

    override suspend fun getPhotoByLocation(
        latitude: Double,
        longitude: Double
    ): PhotoSource.Result {
        return try {
            val result = api.getPhotosByLocation(latitude, longitude)
            println(result.toString())
            if (result.status == STATUS_OK) {
                PhotoSource.Result.Success(result.result.photos)
            } else {
                PhotoSource.Result.Error("${result.code} - ${result.message}")
            }
        } catch (e: UnknownHostException) {
            PhotoSource.Result.Error(e.message ?: "")
        } catch (e: IOException) {
            PhotoSource.Result.Error(e.message ?: "")
        }
    }
}