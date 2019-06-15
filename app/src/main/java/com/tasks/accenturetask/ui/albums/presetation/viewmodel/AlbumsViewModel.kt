package com.tasks.accenturetask.ui.albums.presetation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tasks.accenturetask.base.domain.exception.AccentureException
import com.tasks.accenturetask.base.presentation.model.ObservableResource
import com.tasks.accenturetask.base.presentation.viewmodel.BaseViewModel
import com.tasks.accenturetask.data.local.entity.AlbumEntity
import com.tasks.accenturetask.data.remote.network.response.Album
import com.tasks.accenturetask.ui.albums.data.local.map
import com.tasks.accenturetask.ui.albums.domain.interactor.AddAlbumsLocalUseCase
import com.tasks.accenturetask.ui.albums.domain.interactor.GetAlbumsUseCase
import com.tasks.accenturetask.ui.albums.domain.interactor.GetCachedAlbumsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumsViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val addAlbumsLocalUseCase: AddAlbumsLocalUseCase,
    private val getCachedAlbumsUseCase: GetCachedAlbumsUseCase
) : BaseViewModel() {
    private var albumsList = mutableListOf<Album>()
    val mAlbums = MutableLiveData<List<Album>>()
    val mAlbumsObservable = ObservableResource<String>()

    fun getAlbums() {
        addDisposable(getAlbumsUseCase.build(params = "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mAlbumsObservable.loading.postValue(true)
            }
            .doAfterTerminate {
                mAlbumsObservable.loading.postValue(false)
            }
            .subscribe({
                it?.let {
                    if (it.isNotEmpty()) {
                        albumsList = it.toMutableList()
                        albumsList.sortBy { it.albumTitle }
                        mAlbums.value = albumsList
                        saveAlbums(albumsList)
                    }
                }
            }, {
                (it as? AccentureException).let {
                    mAlbumsObservable.error.value = it
                }
            })
        )
    }

    fun getCachedlAlbums(): LiveData<List<AlbumEntity>> {
        return getCachedAlbumsUseCase.build()
    }

    private fun saveAlbums(albums: MutableList<Album>) {
        if (albums.isNotEmpty()) {
            saveAlbumsInDB(albums.map { it.map() })
        } else {
            val message = "No albums found"
            mAlbumsObservable.value = message
        }
    }

    private fun saveAlbumsInDB(albums: List<AlbumEntity>) {
        addDisposable(addAlbumsLocalUseCase.build(albums)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val message = "Albums have been saved successfully"
                mAlbumsObservable.value = message
            }, {
                val message = "Unexpected Error"
                mAlbumsObservable.value = message
            }
            ))
    }
}