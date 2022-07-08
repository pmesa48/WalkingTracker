package com.pmesa48.pablomesa_challenge.source.dto

import com.google.gson.annotations.SerializedName
import com.pmesa48.pablomesa_challenge.BuildConfig

data class PhotoDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("server")
    val serverId: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("ispublic")
    val isPublic: Boolean
) {
    fun getUrl(): String {
        return "${BuildConfig.FLICKR_API_PHOTO_URL}${serverId}/${id}_${secret}.${BuildConfig.IMAGE_FORMAT}"
    }
}
