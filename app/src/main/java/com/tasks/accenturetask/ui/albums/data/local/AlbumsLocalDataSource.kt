package com.tasks.accenturetask.ui.albums.data.local

import androidx.lifecycle.LiveData
import com.tasks.accenturetask.data.local.dao.AlbumDao
import com.tasks.accenturetask.data.local.entity.AlbumEntity
import io.reactivex.Single
import javax.inject.Inject

class AlbumsLocalDataSource @Inject constructor(private val albumDao: AlbumDao) {

    fun insertAlbums(albums: List<AlbumEntity>): Single<Boolean> =
        Single.create {
            albumDao.insertAlbums(albums)
            it.onSuccess(true)
        }

    fun getCachedAlbums(): LiveData<List<AlbumEntity>> =
        albumDao.getAllAlbums()
}