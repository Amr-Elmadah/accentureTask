package com.tasks.accenturetask.ui.albums.domain.interactor

import com.tasks.accenturetask.data.local.entity.AlbumEntity
import com.tasks.accenturetask.base.domain.interactor.SingleUseCase
import com.tasks.accenturetask.ui.albums.domain.repository.AlbumsRepository
import io.reactivex.Single
import javax.inject.Inject

class AddAlbumsLocalUseCase @Inject constructor(private val repository: AlbumsRepository) :
    SingleUseCase<List<AlbumEntity>, Boolean>() {
    override fun build(params: List<AlbumEntity>): Single<Boolean> =
        repository.insertAlbums(albums = params)
}