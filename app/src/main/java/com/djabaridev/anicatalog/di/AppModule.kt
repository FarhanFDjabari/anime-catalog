package com.djabaridev.anicatalog.di

import android.app.Application
import androidx.room.Room
import com.djabaridev.anicatalog.data.local.AniCatalogDB
import com.djabaridev.anicatalog.data.remote.MyAnimeListAPI
import com.djabaridev.anicatalog.data.remote.interceptor.AniCatalogHeaderInterceptor
import com.djabaridev.anicatalog.data.repositories.AniCatalogRepositoryImpl
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAniCatalogDB(app: Application) : AniCatalogDB {
        return Room.databaseBuilder(
            app,
            AniCatalogDB::class.java,
            AniCatalogDB.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideAniCatalogRepository(
        api: MyAnimeListAPI,
        db: AniCatalogDB
    ) : AniCatalogRepository = AniCatalogRepositoryImpl(api = api, animeDao = db.animeDao, mangaDao = db.mangaDao)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(AniCatalogHeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideMyAnimeListAPI(client: OkHttpClient) : MyAnimeListAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .build()
        .create(MyAnimeListAPI::class.java)
}