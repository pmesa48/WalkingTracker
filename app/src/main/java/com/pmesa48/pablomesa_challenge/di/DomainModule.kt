package com.pmesa48.pablomesa_challenge.di

import com.pmesa48.pablomesa_challenge.domain.usecase.changeservicestatus.ChangeServiceStatusUseCase
import com.pmesa48.pablomesa_challenge.domain.usecase.changeservicestatus.ChangeServiceStatusUseCaseImpl
import com.pmesa48.pablomesa_challenge.domain.usecase.endactivity.EndActivityUseCase
import com.pmesa48.pablomesa_challenge.domain.usecase.endactivity.EndActivityUseCaseImpl
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.FilterLocationByThresholdManager
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.FilterLocationByThresholdUseCase
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.FilterLocationByThresholdUseCaseImpl
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers.EntryMapper
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers.LocationMapper
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.mappers.PhotoMapper
import com.pmesa48.pablomesa_challenge.domain.usecase.getentries.GetEntriesUseCase
import com.pmesa48.pablomesa_challenge.domain.usecase.getentries.GetEntriesUseCaseImpl
import com.pmesa48.pablomesa_challenge.model.repository.EntryRepository
import com.pmesa48.pablomesa_challenge.model.repository.TrackerStatusRepository
import com.pmesa48.pablomesa_challenge.source.api.photos.PhotoSource
import com.pmesa48.pablomesa_challenge.source.cache.entries.EntryCache
import com.pmesa48.pablomesa_challenge.source.cache.trackerstatus.TrackerStatusCache
import com.pmesa48.pablomesa_challenge.source.device.LocationSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideFilterLocationByThresholdUseCase(
        dataManager: FilterLocationByThresholdManager,
        entryRepository: EntryRepository
    ): FilterLocationByThresholdUseCase {
        return FilterLocationByThresholdUseCaseImpl(
            dataManager,
            entryRepository
        )
    }

    @Provides
    fun provideFilterLocationByThresholdManager(
        photoSource: PhotoSource,
        locationSource: LocationSource,
        locationMapper: LocationMapper,
        photoMapper: PhotoMapper,
        entryMapper: EntryMapper
    ): FilterLocationByThresholdManager {
        return FilterLocationByThresholdManager(
            photoSource, locationSource, locationMapper, photoMapper, entryMapper
        )
    }

    @Provides
    fun provideServiceStatusRepository(
        trackerStatusCache: TrackerStatusCache
    ): TrackerStatusRepository {
        return TrackerStatusRepository(trackerStatusCache)
    }


    @Provides
    fun provideEntryRepository(
        entryCache: EntryCache
    ): EntryRepository {
        return EntryRepository(entryCache)
    }

    @Provides
    fun provideServiceStatusUseCase(
        trackerStatusRepository: TrackerStatusRepository
    ): ChangeServiceStatusUseCase {
        return ChangeServiceStatusUseCaseImpl(trackerStatusRepository)
    }


    @Provides
    fun providesGetEntriesUseCase(
        entryRepository: EntryRepository
    ): GetEntriesUseCase {
        return GetEntriesUseCaseImpl(entryRepository)
    }


    @Provides
    fun providesEndWorkoutUseCase(
        entryRepository: EntryRepository
    ): EndActivityUseCase {
        return EndActivityUseCaseImpl(entryRepository)
    }
}