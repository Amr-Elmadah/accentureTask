package com.tasks.accenturetask.data.remote.network.response

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AlbumTest {
    private lateinit var json: String
    private lateinit var album: Album

    @Before
    fun setup() {
        json =
            "{\n" +
                    "    \"userId\": 1,\n" +
                    "    \"id\": 1,\n" +
                    "    \"title\": \"quidem molestiae enim\"\n" +
                    "  }"
        album = Gson().fromJson<Album>(json, Album::class.java)
    }

    @Test
    fun checkNameNotBlank() {
        Assert.assertTrue(album.albumTitle.isNotBlank())
    }
}