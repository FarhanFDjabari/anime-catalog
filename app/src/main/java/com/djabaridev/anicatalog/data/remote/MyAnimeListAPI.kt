package com.djabaridev.anicatalog.data.remote

import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeDetailResponse
import com.djabaridev.anicatalog.data.remote.responses.animanga.AniMangaListItemResponse
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyAnimeListAPI {
    @GET("anime")
    suspend fun getAnimeList(
        @Query("q") keyword: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String,
    ): Response<AniMangaListItemResponse>

    @GET("anime/{id}")
    suspend fun getAnimeDetail(
        @Path("id") id: Int,
        @Query("fields") fields: String,
    ): Response<AnimeDetailResponse>

    @GET("anime/season/{year}/{season}")
    suspend fun getAnimeSeasonal(
        @Path("year") year: Int,
        @Path("season") season: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String,
    ): Response<AniMangaListItemResponse>

    @GET("anime/suggestions")
    suspend fun getAnimeSuggestions(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<AniMangaListItemResponse>

    @GET("manga")
    suspend fun getMangaList(
        @Query("q") keyword: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String,
    ): Response<AniMangaListItemResponse>

    @GET("manga/{id}")
    suspend fun getMangaDetail(
        @Path("id") id: Int,
        @Query("fields") fields: String,
    ): Response<MangaDetailResponse>

    @GET("manga/ranking")
    suspend fun getMangaRanking(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ranking_type") rankingType: String,
        @Query("fields") fields: String,
    ): Response<AniMangaListItemResponse>

    @GET("anime/ranking")
    suspend fun getAnimeRanking(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ranking_type") rankingType: String,
        @Query("fields") fields: String,
    ): Response<AniMangaListItemResponse>
}
