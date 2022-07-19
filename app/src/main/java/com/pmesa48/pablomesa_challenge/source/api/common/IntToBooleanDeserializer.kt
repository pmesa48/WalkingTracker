package com.pmesa48.pablomesa_challenge.source.api.common

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import javax.inject.Inject

class IntToBooleanDeserializer @Inject constructor(): JsonDeserializer<Boolean?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Boolean? {
        return json?.let { json.asInt == 1 }
    }
}