package com.pmesa48.pablomesa_challenge.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.pmesa48.pablomesa_challenge.BuildConfig
import com.pmesa48.pablomesa_challenge.source.api.common.FlickrInterceptor
import com.pmesa48.pablomesa_challenge.source.api.common.IntToBooleanDeserializer
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSourceImpl
import com.pmesa48.pablomesa_challenge.source.cache.entries.EntryCache
import com.pmesa48.pablomesa_challenge.source.cache.entries.EntryCacheImpl
import com.pmesa48.pablomesa_challenge.source.cache.entries.EntryDao
import com.pmesa48.pablomesa_challenge.source.cache.trackerstatus.TrackerStatusCache
import com.pmesa48.pablomesa_challenge.source.cache.trackerstatus.TrackerStatusCacheImpl
import com.pmesa48.pablomesa_challenge.source.cache.trackerstatus.TrackerStatusDao
import com.pmesa48.pablomesa_challenge.source.device.FusedLocationSource
import com.pmesa48.pablomesa_challenge.source.device.LocationSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SourceModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        paramInterceptor: FlickrInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.FLICKR_API_BASE_URL)
            .client(
                OkHttpClient()
                    .newBuilder().callTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(paramInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Provides
    fun providesGson(intToBooleanDeserializer: IntToBooleanDeserializer) : Gson = Gson()
        .newBuilder()
        .registerTypeAdapter(Boolean::class.java, intToBooleanDeserializer)
        .create()

    @Provides
    fun provideLocationSource(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationSource {
        return FusedLocationSource(fusedLocationProviderClient)
    }

    @Provides
    fun providesFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun providesServiceStatusCache(
        trackerStatusDao: TrackerStatusDao
    ): TrackerStatusCache {
        return TrackerStatusCacheImpl(trackerStatusDao)
    }

    @Provides
    fun providesServiceApi(
        retrofit: Retrofit
    ): PhotoSource {
        return PhotoSourceImpl(retrofit)
    }

    @Provides
    fun providesFlickrInterceptor(): FlickrInterceptor {
        return FlickrInterceptor()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideEntryCache(entryDao: EntryDao): EntryCache {
        return EntryCacheImpl(entryDao)
    }
}