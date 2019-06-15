package com.tasks.accenturetask.injection.modules

import com.tasks.accenturetask.BuildConfig
import com.tasks.accenturetask.data.remote.network.retrofit.AccentureAPI
import com.tasks.accenturetask.data.remote.network.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    object DAGGER_CONSTANTS {
        const val BASE_URL = "baseUrlString"
    }

    private object ApiEndpointsConstants {
        const val ProductionURL = "https://jsonplaceholder.typicode.com/"
        const val StagingURL = "https://jsonplaceholder.typicode.com/"
    }

    @Provides
    @Singleton
    @Named(value = DAGGER_CONSTANTS.BASE_URL)
    fun providesBaseUrl() =
        if (BuildConfig.QA) ApiEndpointsConstants.StagingURL else ApiEndpointsConstants.ProductionURL

    @Provides
    @Singleton
    fun provideHttpClient() = OkHttpClient()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()

    @Provides
    @Singleton
    fun provideRetrofitClient(
        @Named(DAGGER_CONSTANTS.BASE_URL) baseURL: String, httpClient: OkHttpClient,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        builder: Retrofit.Builder
    )
            : Retrofit = RetrofitClient(
        baseURL = baseURL,
        httpClient = httpClient.newBuilder(),
        httpLoggingInterceptor = httpLoggingInterceptor,
        builder = builder
    ).getInstance()

    @Provides
    @Singleton
    fun provideAccentureApi(retrofit: Retrofit)
            : AccentureAPI = retrofit.create(AccentureAPI::class.java)

}