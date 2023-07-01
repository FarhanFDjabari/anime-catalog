package com.djabaridev.anicatalog.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.djabaridev.anicatalog.data.local.dao.AnimeDao
import com.djabaridev.anicatalog.data.local.dao.MangaDao
import com.djabaridev.anicatalog.data.local.models.AnimeListItemEntry
import com.djabaridev.anicatalog.data.local.models.MangaListItemEntry
import com.djabaridev.anicatalog.data.remote.MyAnimeListAPI
import com.djabaridev.anicatalog.data.remote.paging.AnimePagingSource
import com.djabaridev.anicatalog.data.remote.paging.MangaPagingSource
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeDetailResponse
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeRankingEnum
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaDetailResponse
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaRankingEnum
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.entities.AniMangaResponseEntity
import com.djabaridev.anicatalog.domain.mapper.toAniMangaListItemEntity
import com.djabaridev.anicatalog.domain.mapper.toAnimeListItemEntry
import com.djabaridev.anicatalog.domain.mapper.toMangaListItemEntry
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException
import javax.inject.Inject

@ActivityScoped
class AniCatalogRepositoryImpl @Inject constructor(
    private val api: MyAnimeListAPI,
    private val animeDao: AnimeDao,
    private val mangaDao: MangaDao
) : AniCatalogRepository {
    override fun getAnimeListFromCache(): Flow<List<AnimeListItemEntry>> {
        return animeDao.getAllAnime()
    }

    override fun getMangaListFromCache(): Flow<List<MangaListItemEntry>> {
        return mangaDao.getAllManga()
    }

    override suspend fun getAnimeList(
        keyword: String,
        limit: Int,
        offset: Int
    ): Resource<AniMangaResponseEntity> {
        return try {
            val fields = "id,title,main_picture,alternative_titles,synopsis,mean,status,genres,num_episodes,average_episode_duration"
            val response = api.getAnimeList(keyword, limit, offset, fields)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(
                    AniMangaResponseEntity(
                        animangas = body.data.map { it.node.toAniMangaListItemEntity() },
                        paging = body.paging,
                    )
                )
            } else {
                Resource.Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            Resource.Error(e.code(), e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }

    override suspend fun getMangaList(
        keyword: String,
        limit: Int,
        offset: Int
    ): Resource<AniMangaResponseEntity> {
        return try {
            val fields = "id,title,main_picture,alternative_titles,synopsis,mean,status,genres,num_volumes,num_chapters,authors{first_name,last_name}"
            val response = api.getMangaList(keyword, limit, offset, fields)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(
                    AniMangaResponseEntity(
                        animangas = body.data.map { it.node.toAniMangaListItemEntity() },
                        paging = body.paging,
                    )
                )
            } else {
                Resource.Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            Resource.Error(e.code(), e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }

    override suspend fun getMangaRanking(
        rankingType: MangaRankingEnum,
        limit: Int,
        offset: Int
    ): Resource<AniMangaResponseEntity> {
        return try {
            val fields = "id,title,main_picture,alternative_titles,synopsis,mean,status,genres,num_volumes,num_chapters,authors{first_name,last_name}"
            val response = api.getMangaRanking(limit, offset, rankingType.name, fields)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                if (offset == 0) mangaDao.insertAllManga(body.data.map{it.node.toMangaListItemEntry()})
                Resource.Success(
                    AniMangaResponseEntity(
                        animangas = body.data.map { it.node.toAniMangaListItemEntity() },
                        paging = body.paging,
                    )
                )
            } else {
                Resource.Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            Resource.Error(e.code(), e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }

    override suspend fun getAnimeRanking(
        rankingType: AnimeRankingEnum,
        limit: Int,
        offset: Int
    ): Resource<AniMangaResponseEntity> {
        return try {
            val fields = "id,title,main_picture,mean"
            val response = api.getAnimeRanking(limit, offset, rankingType.name.lowercase(), fields)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(
                    AniMangaResponseEntity(
                        animangas = body.data.map { it.node.toAniMangaListItemEntity() },
                        paging = body.paging,
                    )
                )
            } else {
                Resource.Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            Resource.Error(e.code(), e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }

    }

    override suspend fun getAnimeDetail(id: Int): Resource<AnimeDetailResponse> {
        return try {
            val fields = "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics"
            val response = api.getAnimeDetail(id, fields)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                Resource.Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            Resource.Error(code = e.code(), message = e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }

    override suspend fun getMangaDetail(id: Int): Resource<MangaDetailResponse> {
        return try {
            val fields = "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_volumes,num_chapters,authors{first_name,last_name},pictures,background,related_anime,related_manga,recommendations,serialization{name}"
            val response = api.getMangaDetail(id, fields)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                Resource.Error(code = response.code(), message = response.message())
            }
        } catch(e: HttpException) {
            Resource.Error(code = e.code(), message = e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }

    override fun getMangaFromCache(id: Int): Flow<MangaListItemEntry?> {
        return mangaDao.getManga(id)
    }

    override fun getAnimeFromCache(id: Int): Flow<AnimeListItemEntry?> {
        return animeDao.getAnime(id)
    }

    override suspend fun updateAnimeIsFavorite(isFavorite: Boolean, anime: AniMangaListItemEntity) {
        val isAnimeExist = animeDao.getAnime(anime.id).firstOrNull() != null
        if (!isAnimeExist) {
            animeDao.insertAnime(
                anime.copy(isFavorite = isFavorite).toAnimeListItemEntry(),
            )
        } else {
            animeDao.updateAnimeIsFavorite(
                anime.copy(isFavorite = isFavorite).toAnimeListItemEntry()
            )
        }
    }

    override suspend fun updateMangaIsFavorite(isFavorite: Boolean, manga: AniMangaListItemEntity) {
        val isMangaExist = mangaDao.getManga(manga.id).firstOrNull() != null
        if (!isMangaExist) {
            mangaDao.insertManga(
                manga.copy(isFavorite = isFavorite).toMangaListItemEntry(),
            )
        } else {
            mangaDao.updateMangaIsFavorite(
                manga.copy(isFavorite = isFavorite).toMangaListItemEntry()
            )
        }
    }

    override fun getFavoriteAnimeList(): Flow<List<AnimeListItemEntry>> {
        return animeDao.getFavoriteAnime()
    }

    override fun getFavoriteMangaList(): Flow<List<MangaListItemEntry>> {
        return mangaDao.getFavoriteManga()
    }

    override suspend fun getAnimeSeasonal(
        year: Int,
        season: String,
        limit: Int,
        offset: Int
    ): Resource<AniMangaResponseEntity> {
        return try {
            val fields = "id,title,main_picture,alternative_titles,synopsis,mean,status,genres,num_episodes,average_episode_duration"
            val response = api.getAnimeSeasonal(year, season, limit, offset, fields)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val entryItems = body.data.map{it.node.toAnimeListItemEntry()}
                if (offset == 0) animeDao.insertAllAnime(entryItems)
                Resource.Success(
                    AniMangaResponseEntity(
                        animangas = entryItems.map { it.toAniMangaListItemEntity() },
                        paging = body.paging,
                        season = body.season
                    )
                )
            } else {
                Resource.Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            Resource.Error(code = e.code(), message = e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }

    override fun getAnimeSeasonalPagination(
        season: String,
        year: Int
    ): Flow<PagingData<AniMangaListItemEntity>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = {
            AnimePagingSource(
                myAnimeListAPI = api,
                season = season,
                year = year,
            )
        }
    ).flow

    override fun getMangaRankPagination(
        sortBy: String
    ): Flow<PagingData<AniMangaListItemEntity>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = {
            MangaPagingSource(
                myAnimeListAPI = api,
                rankingType = sortBy,
            )
        }
    ).flow
}