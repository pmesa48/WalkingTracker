package com.pmesa48.pablomesa_challenge.source.cache.trackerstatus

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.pmesa48.pablomesa_challenge.model.TrackerStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerStatusDao {

    @Insert(onConflict = REPLACE)
    fun add(trackerStatus: TrackerStatus)

    @Query("SELECT * FROM tracker_status WHERE id= :id")
    fun get(id: Int): Flow<TrackerStatus>

    @Query("SELECT * FROM tracker_status WHERE id= :id")
    fun getSingle(id: Int): TrackerStatus?
}
