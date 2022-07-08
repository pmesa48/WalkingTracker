package com.pmesa48.pablomesa_challenge.source.dto

import com.google.gson.annotations.SerializedName

data class GetPhotoByLocationDto(
    @SerializedName("photos")
    val result: PhotosPageInfoDto,
    @SerializedName("stat")
    val status: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)
