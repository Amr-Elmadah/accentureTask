package com.tasks.accenturetask.ui.albums.domain.interactor

import androidx.lifecycle.LiveData
import com.tasks.accenturetask.data.local.entity.AlbumEntity
import com.tasks.accenturetask.ui.albums.domain.repository.AlbumsRepository
import javax.inject.Inject

class GetCachedAlbumsUseCase @Inject constructor(private val repository: AlbumsRepository) {
    fun build(): LiveData<List<AlbumEntity>> =
        repository.getCachedAlbums()
}