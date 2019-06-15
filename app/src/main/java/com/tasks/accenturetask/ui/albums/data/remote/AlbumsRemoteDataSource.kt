package com.tasks.accenturetask.ui.albums.data.remote

import com.tasks.accenturetask.data.remote.network.response.Album
import com.tasks.accenturetask.data.remote.network.retrofit.AccentureAPI
import io.reactivex.Single
import javax.inject.Inject

class AlbumsRemoteDataSource @Inject constructor(private val accentureAPI: AccentureAPI) {

    fun getAlbums(): Single<List<Album>> =
        accentureAPI.loadAlbums()
}