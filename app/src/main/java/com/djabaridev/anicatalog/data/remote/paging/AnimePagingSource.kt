package com.djabaridev.anicatalog.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.djabaridev.anicatalog.data.remote.MyAnimeListAPI
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.mapper.toAniMangaListItemEntity

class AnimePagingSource(
    private val myAnimeListAPI: MyAnimeListAPI,
    private val season: String,
    private val year: Int,
): PagingSource<Int, AniMangaListItemEntity>() {
    override fun getRefreshKey(state: PagingState<Int, AniMangaListItemEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AniMangaListItemEntity> {
        return try {
            val fields = "id,title,main_picture,alternative_titles,synopsis,mean,status,genres,num_episodes,average_episode_duration"
            val page = params.key ?: 0
            val response = myAnimeListAPI.getAnimeSeasonal(
                season = season,
                year = year,
                limit = 10,
                offset = page * 10,
                fields = fields
            )

            LoadResult.Page(
                data = response.body()?.data?.map { it.node.toAniMangaListItemEntity() }?: emptyList(),
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (response.body()?.paging?.next?.isEmpty() == true) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}