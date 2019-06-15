package com.tasks.accenturetask.ui.albums.injection

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.tasks.accenturetask.data.local.dao.AlbumDao
import com.tasks.accenturetask.data.remote.network.retrofit.AccentureAPI
import com.tasks.accenturetask.ui.albums.data.local.AlbumsLocalDataSource
import com.tasks.accenturetask.ui.albums.data.remote.AlbumsRemoteDataSource
import com.tasks.accenturetask.ui.albums.domain.interactor.AddAlbumsLocalUseCase
import com.tasks.accenturetask.ui.albums.domain.interactor.GetAlbumsUseCase
import com.tasks.accenturetask.ui.albums.domain.interactor.GetCachedAlbumsUseCase
import com.tasks.accenturetask.ui.albums.domain.repository.AlbumsRepository
import com.tasks.accenturetask.ui.albums.domain.repository.AlbumsRepositoryImp
import com.tasks.accenturetask.ui.albums.presetation.view.adapter.AlbumAdapter
import com.tasks.accenturetask.ui.albums.presetation.viewmodel.AlbumsViewModel
import dagger.Module
import dagger.Provides

@Module
class AlbumsModule {

    @Provides
    fun provideAlbumsLocalDataSource(albumDao: AlbumDao) =
        AlbumsLocalDataSource(albumDao = albumDao)

    @Provides
    fun provideAlbumsRemoteDataSource(accentureAPI: AccentureAPI) =
        AlbumsRemoteDataSource(accentureAPI = accentureAPI)

    @Provides
    fun provideAlbumRepository(
        remoteDataSource: AlbumsRemoteDataSource,
        localDataSource: AlbumsLocalDataSource
    ): AlbumsRepository =
        AlbumsRepositoryImp(remoteDataSource, localDataSource)

    @Provides
    fun provideGetAlbumsUseCase(repository: AlbumsRepository) =
        GetAlbumsUseCase(repository)

    @Provides
    fun provideAddAlbumsLocalUseCase(repository: AlbumsRepository) =
        AddAlbumsLocalUseCase(repository)

    @Provides
    fun provideAlbumsViewModel(
        getAlbumsUseCase: GetAlbumsUseCase,
        addAlbumsLocalUseCase: AddAlbumsLocalUseCase
        , getCachedAlbumsUseCase: GetCachedAlbumsUseCase
    ) =
        AlbumsViewModel(getAlbumsUseCase, addAlbumsLocalUseCase, getCachedAlbumsUseCase)

    @Provides
    fun provideLinearLayoutManager(context: Context) =
        LinearLayoutManager(context)

    @Provides
    fun provideAlbumAdapter() =
        AlbumAdapter()
}