package com.djabaridev.anicatalog.domain.repositories

import com.djabaridev.anicatalog.data.local.models.AnimeListItemEntry
import com.djabaridev.anicatalog.data.local.models.MangaListItemEntry
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeDetailResponse
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeRankingEnum
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaDetailResponse
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaRankingEnum
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.entities.AniMangaResponseEntity
import com.djabaridev.anicatalog.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AniCatalogRepository {
    fun getAnimeListFromCache(): Flow<List<AnimeListItemEntry>>
    fun getMangaListFromCache(): Flow<List<MangaListItemEntry>>
    suspend fun getAnimeList(keyword: String, limit: Int, offset: Int): Resource<AniMangaResponseEntity>
    suspend fun getMangaList(keyword: String, limit: Int, offset: Int): Resource<AniMangaResponseEntity>
    suspend fun getMangaRanking(rankingType: MangaRankingEnum, limit: Int, offset: Int): Resource<AniMangaResponseEntity>
    suspend fun getAnimeRanking(rankingType: AnimeRankingEnum, limit: Int, offset: Int): Resource<AniMangaResponseEntity>
    suspend fun getAnimeDetail(id: Int): Resource<AnimeDetailResponse>
    suspend fun getMangaDetail(id: Int): Resource<MangaDetailResponse>
    fun getMangaFromCache(id: Int): Flow<MangaListItemEntry?>
    fun getAnimeFromCache(id: Int): Flow<AnimeListItemEntry?>
    suspend fun updateAnimeIsFavorite(isFavorite: Boolean, anime: AniMangaListItemEntity)
    suspend fun updateMangaIsFavorite(isFavorite: Boolean, manga: AniMangaListItemEntity)
    fun getFavoriteAnimeList(): Flow<List<AnimeListItemEntry>>
    fun getFavoriteMangaList(): Flow<List<MangaListItemEntry>>
    suspend fun getAnimeSeasonal(year: Int, season: String, limit: Int, offset: Int): Resource<AniMangaResponseEntity>
}