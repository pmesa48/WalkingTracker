package com.pmesa48.pablomesa_challenge.source.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pmesa48.pablomesa_challenge.model.Entry
import com.pmesa48.pablomesa_challenge.model.TrackerStatus
import com.pmesa48.pablomesa_challenge.source.cache.entries.EntryDao
import com.pmesa48.pablomesa_challenge.source.cache.trackerstatus.TrackerStatusDao

@Database(entities = [TrackerStatus::class, Entry::class], version = 2)
abstract class WorkoutDatabase : RoomDatabase() {

    abstract fun serviceStatusDao(): TrackerStatusDao

    abstract fun entryDao(): EntryDao
}