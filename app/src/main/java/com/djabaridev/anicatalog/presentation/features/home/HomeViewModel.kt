package com.djabaridev.anicatalog.presentation.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeRankingEnum
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaRankingEnum
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.usecases.GetAnimeRanking
import com.djabaridev.anicatalog.domain.usecases.GetAnimeSeasonal
import com.djabaridev.anicatalog.domain.usecases.GetMangaRanking
import com.djabaridev.anicatalog.domain.usecases.UpdateAnimeIsFavorite
import com.djabaridev.anicatalog.domain.usecases.UpdateMangaIsFavorite
import com.djabaridev.anicatalog.utils.ListState
import com.djabaridev.anicatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAnimeSeasonal: GetAnimeSeasonal,
    private val getMangaRanking: GetMangaRanking,
    private val getAnimeRanking: GetAnimeRanking,
    private val updateAnimeIsFavorite: UpdateAnimeIsFavorite,
    private val updateMangaIsFavorite: UpdateMangaIsFavorite,
) : ViewModel() {

    val seasonalAnimeList = mutableStateListOf<AniMangaListItemEntity>()
    val mangaRankList = mutableStateListOf<AniMangaListItemEntity>()
    val animeRankingList = mutableStateListOf<AniMangaListItemEntity>()

    private var seasonalAnimePage by mutableIntStateOf(0)
    private var mangaRankPage by mutableIntStateOf(0)
    var canSeasonalAnimePaginate by mutableStateOf(false)
    var canMangaRankPaginate by mutableStateOf(false)
    var seasonalAnimeListState by mutableStateOf(ListState.IDLE)
    var mangaRankListState by mutableStateOf(ListState.IDLE)

    init {
        getAnimeRanking()
        getAnimeSeasonal()
        getMangaRanking()
    }

    fun getAnimeSeasonal() = viewModelScope.launch(Dispatchers.IO) {
        if (canSeasonalAnimePaginate && seasonalAnimeListState == ListState.IDLE) {
            seasonalAnimeListState = if (seasonalAnimePage == 0) ListState.LOADING else ListState.PAGINATING
            val result = getAnimeSeasonal.execute()
            if (result is Resource.Success) {
                canSeasonalAnimePaginate  = result.data.paging?.next != null
                if (seasonalAnimePage == 0) seasonalAnimeList.clear()
                seasonalAnimeList.addAll(result.data.animangas)
                seasonalAnimeListState = ListState.IDLE
                if (canSeasonalAnimePaginate) seasonalAnimePage++
            } else {
                seasonalAnimeListState = if (seasonalAnimePage == 0) ListState.ERROR else ListState.PAGINATION_EXHAUST
            }
        }
    }

    fun getMangaRanking() = viewModelScope.launch(Dispatchers.IO) {
        if (canMangaRankPaginate && mangaRankListState == ListState.IDLE) {
            mangaRankListState = if (mangaRankPage == 0) ListState.LOADING else ListState.PAGINATING
            val result = getMangaRanking.execute(MangaRankingEnum.bypopularity)
            if (result is Resource.Success) {
                canMangaRankPaginate  = result.data.paging?.next != null
                if (mangaRankPage == 0) mangaRankList.clear()
                mangaRankList.addAll(result.data.animangas)
                mangaRankListState = ListState.IDLE
                if (canMangaRankPaginate) mangaRankPage++
            } else {
                mangaRankListState = if (mangaRankPage == 0) ListState.ERROR else ListState.PAGINATION_EXHAUST
            }
        }
    }

    fun getAnimeRanking() = viewModelScope.launch(Dispatchers.IO) {
        val result = getAnimeRanking.execute(AnimeRankingEnum.FAVORITE)
        if (result is Resource.Success) {
            animeRankingList.clear()
            animeRankingList.addAll(result.data.animangas)
        }
    }

    fun onAnimeIsFavoriteClick(isFavorite: Boolean, anime: AniMangaListItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        updateAnimeIsFavorite.execute(isFavorite, anime)
    }

    fun onMangaIsFavoriteClick(isFavorite: Boolean, manga: AniMangaListItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        updateMangaIsFavorite.execute(isFavorite, manga)
    }

    override fun onCleared() {
        seasonalAnimePage = 0
        seasonalAnimeListState = ListState.IDLE
        canSeasonalAnimePaginate = false
        mangaRankPage = 0
        mangaRankListState = ListState.IDLE
        canMangaRankPaginate = false
        super.onCleared()
    }
}