package com.tasks.accenturetask.injection

import android.app.Application
import com.tasks.accenturetask.App
import com.tasks.accenturetask.injection.context.ActivityBuilder
import com.tasks.accenturetask.injection.modules.AlbumsDatabaseModule
import com.tasks.accenturetask.injection.modules.AppModule
import com.tasks.accenturetask.injection.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        AlbumsDatabaseModule::class,
        NetworkModule::class]
)
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}