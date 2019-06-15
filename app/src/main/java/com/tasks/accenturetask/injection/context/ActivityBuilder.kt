package com.tasks.accenturetask.injection.context

import com.tasks.accenturetask.ui.albums.injection.AlbumsModule
import com.tasks.accenturetask.ui.albums.presetation.view.activity.AlbumsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(AlbumsModule::class)])
    abstract fun bindAlbumsActivity(): AlbumsActivity
}

