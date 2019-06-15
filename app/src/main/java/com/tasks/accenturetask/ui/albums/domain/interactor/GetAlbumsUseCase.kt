package com.tasks.accenturetask.ui.albums.domain.interactor

import com.tasks.accenturetask.base.domain.interactor.ListUseCase
import com.tasks.accenturetask.data.remote.network.response.Album
import com.tasks.accenturetask.ui.albums.domain.repository.AlbumsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(private val repository: AlbumsRepository) :
    ListUseCase<String, Album>() {
    override fun build(params: String): Single<List<Album>> =
        repository.getAlbums()
}