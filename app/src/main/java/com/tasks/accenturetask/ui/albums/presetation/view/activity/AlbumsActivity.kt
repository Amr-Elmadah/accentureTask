package com.tasks.accenturetask.ui.albums.presetation.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tasks.accenturetask.R
import com.tasks.accenturetask.base.presentation.view.extension.showSnack
import com.tasks.accenturetask.base.presentation.viewmodel.ViewModelFactory
import com.tasks.accenturetask.data.remote.network.response.Album
import com.tasks.accenturetask.ui.albums.data.local.mapToUI
import com.tasks.accenturetask.ui.albums.presetation.view.adapter.AlbumAdapter
import com.tasks.accenturetask.ui.albums.presetation.viewmodel.AlbumsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_albums.*
import javax.inject.Inject

class AlbumsActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AlbumsViewModel>

    private val mViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AlbumsViewModel::class.java)
    }

    @Inject
    lateinit var manager: LinearLayoutManager

    @Inject
    lateinit var adapter: AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_albums)
        setupControllers()
    }

    override fun onStart() {
        super.onStart()
        getAlbums()
    }

    private fun setupControllers() {
        setupToolbar()
        setupRecyclerView()
        observeAlbumsChange()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun getAlbums() {
        supportActionBar?.title = getString(R.string.album_list)
        mViewModel.getAlbums()
    }

    private fun setupRecyclerView() {
        manager.orientation = RecyclerView.VERTICAL
        rvAlbums.layoutManager = manager
        rvAlbums.adapter = adapter
        srlAlbums.setOnRefreshListener { mViewModel.getAlbums() }
    }

    @SuppressLint("CheckResult")
    private fun observeAlbumsChange() {
        mViewModel.mAlbums.observe(this, Observer { albums ->
            albums?.let {
                srlAlbums.isRefreshing = false
                adapter.replaceAllItems(it.toMutableList())
            }
        })
        mViewModel.mAlbumsObservable.observe(this,
            successObserver = Observer {
                it?.let {
                    srlAlbums.isRefreshing = false
                    llMainContent.showSnack(it)
                }
            }, commonErrorObserver = Observer {
                srlAlbums.isRefreshing = false
            }, loadingObserver = Observer {
                it?.let {
                    srlAlbums.isRefreshing = true
                }
            }, networkErrorConsumer = Observer {
                srlAlbums.isRefreshing = false
                llMainContent.showSnack(
                    getString(R.string.internet_connection),
                    Snackbar.LENGTH_LONG
                )
                mViewModel.getCachedlAlbums().observe(this, Observer { it ->
                    if (it != null) {
                        srlAlbums.isRefreshing = false
                        if (it.isNotEmpty()) {
                            llNoData.visibility = View.GONE
                            adapter.replaceAllItems(it.map { it.mapToUI() }.toMutableList())
                        } else {
                            adapter.getItems().clear()
                            adapter.notifyDataSetChanged()
                            llNoData.visibility = View.VISIBLE
                        }
                    }
                })
            })

        adapter.getViewClickedObservable().subscribe {
            it?.let {
                openAlbumDetailsActivity(it)
            }
        }
    }

    private fun openAlbumDetailsActivity(album: Album) {
        //TODO: If required to open details activity of the clicked album
    }
}
