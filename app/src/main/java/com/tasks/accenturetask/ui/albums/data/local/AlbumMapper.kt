package com.tasks.accenturetask.ui.albums.data.local

import com.tasks.accenturetask.data.local.entity.AlbumEntity
import com.tasks.accenturetask.data.remote.network.response.Album


fun AlbumEntity.mapToUI(): Album {
    return Album(
        albumID = this.albumID
        , albumTitle = this.albumTitle
        , userID = this.userId
    )
}

fun Album.map(): AlbumEntity {
    return AlbumEntity(
        albumID = this.albumID
        , albumTitle = this.albumTitle
        , userId = this.userID
    )
}