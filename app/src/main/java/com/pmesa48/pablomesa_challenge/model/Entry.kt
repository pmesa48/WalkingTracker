package com.pmesa48.pablomesa_challenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class Entry(
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @PrimaryKey(autoGenerate = false)
    val date: Long,
    @ColumnInfo(name = "imagePath")
    val imagePath: String
)
