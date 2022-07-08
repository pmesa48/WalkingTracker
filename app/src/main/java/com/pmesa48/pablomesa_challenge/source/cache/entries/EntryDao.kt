package com.pmesa48.pablomesa_challenge.source.cache.entries

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.pmesa48.pablomesa_challenge.model.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Insert(onConflict = REPLACE)
    fun add(entry: Entry)

    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getAll(): Flow<List<Entry>>

    @Query("SELECT * FROM entries ORDER BY date DESC LIMIT 1")
    suspend fun getLast(): Entry

    @Query("DELETE FROM entries")
    fun nuke()
}
