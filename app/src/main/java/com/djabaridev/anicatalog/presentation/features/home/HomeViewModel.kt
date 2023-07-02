package com.djabaridev.anicatalog.presentation.features.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeRankingEnum
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaRankingEnum
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.mapper.toAnimeListEntities
import com.djabaridev.anicatalog.domain.mapper.toMangaListEntities
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.ListState
import com.djabaridev.anicatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AniCatalogRepository,
) : ViewModel() {

    val seasonalAnimeList = mutableStateListOf<AniMangaListItemEntity>()
    val mangaRankList = mutableStateListOf<AniMangaListItemEntity>()
    val animeRankingList = mutableStateListOf<AniMangaListItemEntity>()

    private val _animeRankFilter = mutableStateOf(AnimeRankingEnum.BYPOPULARITY)
    val animeRankFilter: State<AnimeRankingEnum> = _animeRankFilter
    private val _mangaRankFilter = mutableStateOf(MangaRankingEnum.bypopularity)
    val mangaRankFilter: State<MangaRankingEnum> = _mangaRankFilter
    private val _animeSelectedSeason = mutableStateOf("summer")
    val animeSelectedSeason: State<String> = _animeSelectedSeason
    private val _animeSelectedYear = mutableIntStateOf(2023)
    val animeSelectedYear: State<Int> = _animeSelectedYear

    val seasonalAnimeListState = mutableStateOf(ListState.IDLE)
    val animeRankListState = mutableStateOf(ListState.IDLE)
    val mangaRankListState = mutableStateOf(ListState.IDLE)

    private var getAnimeSeasonJob: Job? = null
    private var getAnimeRankJob: Job? = null
    private var getMangaRankJob: Job? = null

    init {
        reload()
    }

    private fun reload() {
        viewModelScope.launch {
            coroutineScope {
                launch {fetchAnimeRanking()}
                launch {fetchAnimeOnSeason("summer", 2023)}
                launch {fetchMangaRanking()}
            }
        }
    }

    fun getAnimeSeasonal(season: String = "summer", year: Int = 2023) {
        _animeSelectedSeason.value = season
        _animeSelectedYear.intValue = year
        getAnimeSeasonJob?.cancel()
        getAnimeSeasonJob = viewModelScope.launch {
            delay(750L)
            fetchAnimeOnSeason(season, year)
        }
    }

    private suspend fun fetchAnimeOnSeason(season: String, year: Int) {
        try {
            if (seasonalAnimeListState.value == ListState.IDLE) {
                seasonalAnimeListState.value =
                    ListState.LOADING
                val result =
                    repository.getAnimeSeasonal(season = season, year = year, limit = 5, offset = 0)
                if (result is Resource.Success) {
                    seasonalAnimeList.clear()
                    seasonalAnimeList.addAll(result.data.animangas)
                    seasonalAnimeListState.value = ListState.IDLE
                } else {
                    repository.getAnimeListFromCache().flowOn(Dispatchers.IO)
                        .collect{
                            if (it.isNotEmpty()) seasonalAnimeList.clear()
                            seasonalAnimeList.addAll(it.toAnimeListEntities())
                            Log.d("HomeViewModel", "getAnimeSeasonal: $it")
                            seasonalAnimeListState.value = ListState.IDLE
                        }
                }
            }
        } catch (e: Exception) {
            Log.d("HomeViewModel", "getAnimeSeasonal: ${e.message}")
            seasonalAnimeListState.value = ListState.ERROR
        }
    }

    fun getMangaRanking(sortBy: MangaRankingEnum) {
        _mangaRankFilter.value = sortBy
        getMangaRankJob?.cancel()
        getMangaRankJob = viewModelScope.launch {
            delay(800L)
            fetchMangaRanking(sortBy)
        }
    }

    private suspend fun fetchMangaRanking(sortBy: MangaRankingEnum = MangaRankingEnum.bypopularity) {
        try {
            if (mangaRankListState.value == ListState.IDLE) {
                mangaRankListState.value = ListState.LOADING
                val result =
                    repository.getMangaRanking(
                        sortBy,
                        limit = 5,
                        offset = 0
                    )
                if (result is Resource.Success) {
                    mangaRankList.clear()
                    mangaRankList.addAll(result.data.animangas)
                    mangaRankListState.value = ListState.IDLE
                } else {
                    repository.getMangaListFromCache().flowOn(Dispatchers.IO).collect{
                        if (it.isNotEmpty()) seasonalAnimeList.clear()
                        mangaRankList.addAll(it.toMangaListEntities())
                        mangaRankListState.value = ListState.IDLE
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("HomeViewModel", "getMangaRanking: ${e.message}")
            mangaRankListState.value = ListState.ERROR
        }
    }

    fun getAnimeRanking(sortBy: AnimeRankingEnum) {
        _animeRankFilter.value = sortBy
        getAnimeRankJob?.cancel()
        getAnimeRankJob = viewModelScope.launch {
            delay(800L)
            fetchAnimeRanking(sortBy)
        }
    }

    private suspend fun fetchAnimeRanking(sortBy: AnimeRankingEnum = AnimeRankingEnum.FAVORITE) {
        try {
            if (animeRankListState.value == ListState.IDLE) {
                animeRankListState.value = ListState.LOADING
                val result =
                    repository.getAnimeRanking(sortBy, limit = 10, offset = 0)
                if (result is Resource.Success) {
                    animeRankingList.clear()
                    animeRankingList.addAll(result.data.animangas)
                    animeRankListState.value = ListState.IDLE
                } else {
                    repository.getAnimeListFromCache().flowOn(Dispatchers.IO).collect{
                        if (it.isNotEmpty()) animeRankingList.clear()
                        animeRankingList.addAll(it.toAnimeListEntities())
                        animeRankListState.value = ListState.IDLE
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("HomeViewModel", "getAnimeRanking: ${e.message}")
            animeRankListState.value = ListState.ERROR
        }
    }

    fun onAnimeIsFavoriteClick(isFavorite: Boolean, anime: AniMangaListItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateAnimeIsFavorite(isFavorite, anime)
    }

    fun onMangaIsFavoriteClick(isFavorite: Boolean, manga: AniMangaListItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateMangaIsFavorite(isFavorite, manga)
    }

    override fun onCleared() {
        seasonalAnimeListState.value = ListState.IDLE
        mangaRankListState.value = ListState.IDLE
        animeRankListState.value = ListState.IDLE
        getAnimeRankJob?.cancel()
        getMangaRankJob?.cancel()
        getAnimeSeasonJob?.cancel()
        super.onCleared()
    }
}