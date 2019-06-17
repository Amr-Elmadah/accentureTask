package com.tasks.accenturetask.ui.albums.presentaion.viewmodel

import com.tasks.accenturetask.data.remote.network.response.Album
import com.tasks.accenturetask.ui.albums.domain.interactor.AddAlbumsLocalUseCase
import com.tasks.accenturetask.ui.albums.domain.interactor.GetAlbumsUseCase
import com.tasks.accenturetask.ui.albums.domain.interactor.GetCachedAlbumsUseCase
import com.tasks.accenturetask.ui.albums.domain.repository.AlbumsRepository
import com.tasks.accenturetask.ui.albums.presetation.viewmodel.AlbumsViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AlbumsViewModelTest {
    @Mock
    private lateinit var albumsRepository: AlbumsRepository

    @Mock
    private lateinit var getAlbumsUseCase: GetAlbumsUseCase

    @Mock
    private lateinit var addAlbumsLocalUseCase: AddAlbumsLocalUseCase

    @Mock
    private lateinit var getCachedAlbumsUseCase: GetCachedAlbumsUseCase

    private lateinit var albumsViewModel: AlbumsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        albumsViewModel =
            AlbumsViewModel(getAlbumsUseCase, addAlbumsLocalUseCase, getCachedAlbumsUseCase)
    }

    @Test
    fun checkResponseOfAlbumsNotEmpty() {
        val albums = listOf(Album(0, 0, "test1"), Album(1, 1, "test2"), Album(2, 2, "test3"))
        Mockito.`when`(albumsRepository.getAlbums())
            .thenReturn(Single.just(albums))
        val response = albumsRepository.getAlbums()
        response.test()
            .assertValue { it.isNotEmpty() }
    }

    @Test
    fun checkResponseOfAlbumsName() {
        val albums = listOf(Album(0, 0, "test1"), Album(1, 1, "test2"), Album(2, 2, "test3"))
        Mockito.`when`(albumsRepository.getAlbums())
            .thenReturn(Single.just(albums))
        val response = albumsRepository.getAlbums()
        for (i in 0 until response.blockingGet().size) {
            response.test()
                .assertValue { it[i].albumTitle == "test" + (i + 1) }
        }
    }
}