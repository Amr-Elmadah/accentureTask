package com.tasks.accenturetask.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    val albumID: Int,
    @SerializedName("userId")
    val userID: Int,
    @SerializedName("title")
    val albumTitle: String
)