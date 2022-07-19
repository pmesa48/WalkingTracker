package com.pmesa48.pablomesa_challenge.source.api.photos

import android.util.Log
import com.google.gson.JsonSyntaxException
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource.Result.Error
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource.Result.Success
import com.pmesa48.pablomesa_challenge.source.dto.GetPhotoByLocationDto
import com.pmesa48.pablomesa_challenge.source.dto.PhotosPageInfoDto
import okio.IOException
import retrofit2.Retrofit
import java.net.UnknownHostException

class PhotoSourceImpl(
    retrofit: Retrofit
) : PhotoSource {

    companion object {
        private const val MISSING_VALUES = 43
        private const val UNKNOWN_HOST_CODE = 42
        private const val IO_CODE = 41
        private const val JSON_SYNTAX_CODE = 40
        private const val STATUS_OK = "ok"
    }

    val api: PhotoServiceApi = retrofit.create(PhotoServiceApi::class.java)

    override suspend fun getPhotoByLocation(
        latitude: Double,
        longitude: Double
    ): PhotoSource.Result {
        return try {
            val result = api.getPhotosByLocation(latitude, longitude)
            if (successfulResponse(result)) {
                Success(result!!.result!!.photos!!)
            } else {
                Error(MISSING_VALUES, result?.message ?: "")
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Error(UNKNOWN_HOST_CODE, e.message ?: "")
        } catch (e: IOException) {
            e.printStackTrace()
            Error(IO_CODE,e.message ?: "")
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            Error(JSON_SYNTAX_CODE, e.message ?: "")
        }
    }

    private fun successfulResponse(
        result: GetPhotoByLocationDto?
    ) = (result?.result != null
            && result.status != null
            && result.result.photos != null
            && result.result.photos.isNotEmpty()
            && result.status == STATUS_OK)

}