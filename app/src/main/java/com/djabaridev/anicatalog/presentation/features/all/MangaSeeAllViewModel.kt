package com.djabaridev.anicatalog.presentation.features.all

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaRankingEnum
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MangaSeeAllViewModel @Inject constructor(
    private val repository: AniCatalogRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _currentMangaRankType = mutableStateOf("bypopularity")
    val currentMangaRankType get() = when(_currentMangaRankType.value) {
        MangaRankingEnum.bypopularity.name -> "Popular Manga"
        MangaRankingEnum.all.name -> "All Manga"
        MangaRankingEnum.favorite.name -> "Trending Manga"
        MangaRankingEnum.manga.name -> "Manga Only"
        MangaRankingEnum.manhwa.name -> "Manhwa Only"
        else -> "Popular Manga"
    }

    init {
        _currentMangaRankType.value = savedStateHandle["sortType"] ?: ""
    }

    val getMangaRank: Flow<PagingData<AniMangaListItemEntity>> = repository.getMangaRankPagination(
        sortBy = _currentMangaRankType.value,
    ).cachedIn(viewModelScope)
}