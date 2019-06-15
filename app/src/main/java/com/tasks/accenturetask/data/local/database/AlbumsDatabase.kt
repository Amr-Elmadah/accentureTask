package com.tasks.accenturetask.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasks.accenturetask.data.local.entity.AlbumEntity
import com.tasks.accenturetask.data.local.dao.AlbumDao

@Database(entities = [AlbumEntity::class], version = 1, exportSchema = false)
abstract class AlbumsDatabase : RoomDatabase() {
    companion object {
        val DATABASE_NAME: String = "AlbumsDataBase"
    }

    abstract fun AlbumDao(): AlbumDao
}