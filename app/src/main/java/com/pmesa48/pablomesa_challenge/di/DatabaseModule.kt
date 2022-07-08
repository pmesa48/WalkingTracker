package com.pmesa48.pablomesa_challenge.di

import android.content.Context
import androidx.room.Room
import com.pmesa48.pablomesa_challenge.source.cache.WorkoutDatabase
import com.pmesa48.pablomesa_challenge.source.cache.entries.EntryDao
import com.pmesa48.pablomesa_challenge.source.cache.trackerstatus.TrackerStatusDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideServiceStatusDao(database: WorkoutDatabase): TrackerStatusDao {
        return database.serviceStatusDao()
    }

    @Provides
    fun provideEntryDao(database: WorkoutDatabase): EntryDao {
        return database.entryDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): WorkoutDatabase {
        return Room.databaseBuilder(
            appContext,
            WorkoutDatabase::class.java,
            "workout.db"
        ).build()
    }
}