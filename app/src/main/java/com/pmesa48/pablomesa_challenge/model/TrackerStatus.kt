package com.pmesa48.pablomesa_challenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracker_status")
data class TrackerStatus(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "active")
    val active: Boolean
)