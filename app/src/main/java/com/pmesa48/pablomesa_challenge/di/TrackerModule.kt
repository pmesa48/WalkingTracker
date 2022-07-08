package com.pmesa48.pablomesa_challenge.di

import com.pmesa48.pablomesa_challenge.domain.usecase.changeservicestatus.ChangeServiceStatusUseCase
import com.pmesa48.pablomesa_challenge.domain.usecase.endactivity.EndActivityUseCase
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.FilterLocationByThresholdUseCase
import com.pmesa48.pablomesa_challenge.framework.tracker.TrackerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrackerModule {

    @Provides
    fun providesTrackerManager(
        filterLocationByThresholdUseCase: FilterLocationByThresholdUseCase,
        changeServiceStatusUseCase: ChangeServiceStatusUseCase,
        endActivityUseCase: EndActivityUseCase
    ): TrackerManager {
        return TrackerManager(
            changeServiceStatusUseCase,
            endActivityUseCase,
            filterLocationByThresholdUseCase
        )
    }
}