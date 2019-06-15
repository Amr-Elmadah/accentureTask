package com.tasks.accenturetask.injection.modules

import android.content.Context
import androidx.room.Room
import com.tasks.accenturetask.data.local.dao.AlbumDao
import com.tasks.accenturetask.data.local.database.AlbumsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AlbumsDatabaseModule {
    @Singleton
    @Provides
    fun provideAlbumsDatabase(context: Context): AlbumsDatabase =
        Room.databaseBuilder(
            context,
            AlbumsDatabase::class.java, AlbumsDatabase.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideAlbumDao(albumsDatabase: AlbumsDatabase): AlbumDao {
        return albumsDatabase.AlbumDao()
    }
}