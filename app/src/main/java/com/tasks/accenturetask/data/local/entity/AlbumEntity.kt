package com.tasks.accenturetask.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Album")
data class AlbumEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var albumID: Int,
    @ColumnInfo(name = "userId")
    var userId: Int,
    @ColumnInfo(name = "title")
    var albumTitle: String
)