package com.tasks.accenturetask.ui.albums.domain.repository

import androidx.lifecycle.LiveData
import com.tasks.accenturetask.data.local.entity.AlbumEntity
import com.tasks.accenturetask.data.remote.network.response.Album
import com.tasks.accenturetask.ui.albums.data.local.AlbumsLocalDataSource
import com.tasks.accenturetask.ui.albums.data.remote.AlbumsRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class AlbumsRepositoryImp @Inject constructor(
    private val remoteDataSource: AlbumsRemoteDataSource,
    private val localDataSource: AlbumsLocalDataSource
) : AlbumsRepository {

    override fun getCachedAlbums(): LiveData<List<AlbumEntity>> =
        localDataSource.getCachedAlbums()


    override fun insertAlbums(albums: List<AlbumEntity>): Single<Boolean> =
        localDataSource.insertAlbums(albums = albums)

    override fun getAlbums(): Single<List<Album>> =
        remoteDataSource.getAlbums()
}