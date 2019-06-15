package com.tasks.accenturetask.ui.albums.domain.repository

import androidx.lifecycle.LiveData
import com.tasks.accenturetask.data.local.entity.AlbumEntity
import com.tasks.accenturetask.data.remote.network.response.Album
import io.reactivex.Single

interface AlbumsRepository {
    fun getAlbums(): Single<List<Album>>

    fun insertAlbums(albums: List<AlbumEntity>): Single<Boolean>

    fun getCachedAlbums(): LiveData<List<AlbumEntity>>
}