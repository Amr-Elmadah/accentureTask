package com.tasks.accenturetask.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tasks.accenturetask.data.local.entity.AlbumEntity

@Dao
interface AlbumDao {
    @Query("SELECT * from Album ORDER BY title ASC")
    fun getAllAlbums(): LiveData<List<AlbumEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbums(album: List<AlbumEntity>)
}
