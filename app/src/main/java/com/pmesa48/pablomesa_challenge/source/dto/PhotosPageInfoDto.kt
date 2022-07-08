package com.pmesa48.pablomesa_challenge.source.dto

import com.google.gson.annotations.SerializedName

data class PhotosPageInfoDto(
    @SerializedName("photo")
    val photos: List<PhotoDto>
)
