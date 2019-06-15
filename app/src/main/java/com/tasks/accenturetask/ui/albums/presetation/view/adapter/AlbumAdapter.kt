package com.tasks.accenturetask.ui.albums.presetation.view.adapter

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasks.accenturetask.R
import com.tasks.accenturetask.base.presentation.view.adapter.BaseRecyclerAdapter
import com.tasks.accenturetask.data.remote.network.response.Album
import com.tasks.accenturetask.base.presentation.view.extension.getInflatedView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_album.view.*


class AlbumAdapter : BaseRecyclerAdapter<Album>() {

    private val mViewClickSubject = PublishSubject.create<Album>()

    fun getViewClickedObservable(): Observable<Album> {
        return mViewClickSubject
    }

    override fun getAdapterPageSize(): Int {
        return PAGE_SIZE
    }

    override fun mainItemView(parent: ViewGroup): View {
        return parent.getInflatedView(R.layout.item_album)
    }


    override fun mainItemViewHolder(view: View): RecyclerView.ViewHolder {
        return AlbumViewHolder(view)
    }

    override fun onBindMainViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AlbumViewHolder) {
            holder.bind(getItems()[position])
            holder.itemView.setOnClickListener {
                mViewClickSubject.onNext(getItems()[position])
            }
        }
    }

    private class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Album) = with(itemView) {
            tvAlbumName.text = item.albumTitle
        }
    }
}