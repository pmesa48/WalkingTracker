package com.pmesa48.pablomesa_challenge.di

import com.pmesa48.pablomesa_challenge.framework.common.LocationPermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object UiModule {

    @Provides
    fun providesLocationPermissionManager(): LocationPermissionManager {
        return LocationPermissionManager()
    }

}