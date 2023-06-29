package com.djabaridev.anicatalog.presentation.features.search

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: AniCatalogRepository,
) : ViewModel() {

    val mangaList = mutableStateListOf<AniMangaListItemEntity>()
    val animeList = mutableStateListOf<AniMangaListItemEntity>()

    private var searchAnime: Job? = null
    private var searchManga: Job? = null

    private val _animeListEventFlow = MutableSharedFlow<UIEvent>()
    val animeListEventFlow = _animeListEventFlow.asSharedFlow()

    private val _mangaListEventFlow = MutableSharedFlow<UIEvent>()
    val mangaListEventFlow = _mangaListEventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            _mangaListEventFlow.emit(UIEvent.Loading)
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchAniManga -> {
                search(event.keyword)
            }
        }
    }

    private fun search(keyword: String) {
        viewModelScope.launch {
            coroutineScope {
                launch {
                    searchAnime(keyword)
                }
                launch {
                    searchManga(keyword)
                }
            }
        }
    }

    private fun searchAnime(keyword: String) {
        searchAnime?.cancel()
        searchAnime = viewModelScope.launch {
            delay(750L)
            fetchAnimeByKeyword(keyword)
        }
    }

    private fun searchManga(keyword: String) {
        searchManga?.cancel()
        searchManga = viewModelScope.launch {
            delay(750L)
            fetchMangaByKeyword(keyword)
        }
    }

    private suspend fun fetchAnimeByKeyword(keyword: String) {
        _animeListEventFlow.emit(UIEvent.Loading)
        when (val result = repository.getAnimeList(keyword, 5, 0)) {
            is Resource.Success -> {
                animeList.clear()
                animeList.addAll(result.data.animangas)
                _animeListEventFlow.emit(UIEvent.DataLoaded)
            }
            is Resource.Error -> {
                _animeListEventFlow.emit(UIEvent.ShowSnackbar("Error ${result.code}: ${result.message}"))
            }
            else -> {
                _animeListEventFlow.emit(UIEvent.ShowSnackbar("Unknown error"))
            }
        }
    }

    private suspend fun fetchMangaByKeyword(keyword: String) {
        _mangaListEventFlow.emit(UIEvent.Loading)
        when (val result = repository.getMangaList(keyword, 5, 0)) {
            is Resource.Success -> {
                mangaList.clear()
                mangaList.addAll(result.data.animangas)
                _mangaListEventFlow.emit(UIEvent.DataLoaded)
            }
            is Resource.Error -> {
                _mangaListEventFlow.emit(UIEvent.ShowSnackbar("Error ${result.code}: ${result.message}"))
            }
            else -> {
                _mangaListEventFlow.emit(UIEvent.ShowSnackbar("Unknown error"))
            }
        }
    }

    override fun onCleared() {
        searchAnime?.cancel()
        searchManga?.cancel()
        super.onCleared()
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object Loading : UIEvent()
        object DataLoaded : UIEvent()
    }
}