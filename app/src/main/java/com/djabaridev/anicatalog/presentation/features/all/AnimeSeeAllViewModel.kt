package com.djabaridev.anicatalog.presentation.features.all

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimeSeeAllViewModel @Inject constructor(
    private val repository: AniCatalogRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _currentAnimeSeason = mutableStateOf("Summer")
    val currentAnimeSeason get() = _currentAnimeSeason.value
    private val _currentAnimeYear = mutableIntStateOf(0)
    val currentAnimeYear get() = _currentAnimeYear.intValue

    init {
        _currentAnimeSeason.value = savedStateHandle["season"] ?: ""
        _currentAnimeYear.intValue = savedStateHandle["year"] ?: 0
    }

    val getAnimeSeasonal: Flow<PagingData<AniMangaListItemEntity>> = repository.getAnimeSeasonalPagination(
        season = _currentAnimeSeason.value,
        year = _currentAnimeYear.intValue,
    ).cachedIn(viewModelScope)

}