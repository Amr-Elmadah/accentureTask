package com.tasks.accenturetask.data.remote.network.retrofit

import com.tasks.accenturetask.data.remote.network.response.Album
import io.reactivex.Single
import retrofit2.http.GET

interface AccentureAPI {
    @GET("albums")
    fun loadAlbums(): Single<List<Album>>
}