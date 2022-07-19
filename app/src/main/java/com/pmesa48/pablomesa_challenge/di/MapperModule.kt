package com.pmesa48.pablomesa_challenge.di

import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers.EntryMapper
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers.LocationMapper
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers.PhotoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun providesEntryDataMapper(): EntryMapper {
        return EntryMapper()
    }

    @Provides
    fun providesLocationMapper(): LocationMapper {
        return LocationMapper()
    }

    @Provides
    fun providesPhotoMapper(): PhotoMapper {
        return PhotoMapper();
    }
}