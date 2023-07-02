package com.djabaridev.anicatalog.presentation.features.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeDetailResponse
import com.djabaridev.anicatalog.domain.mapper.toAniMangaListItemEntity
import com.djabaridev.anicatalog.domain.mapper.toAnimeListItemEntry
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.presentation.theme.BlueGray500
import com.djabaridev.anicatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val repository: AniCatalogRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _animeDetail = mutableStateOf<AnimeDetailResponse?>(null)
    val animeDetail: State<AnimeDetailResponse?> = _animeDetail

    private val _isAnimeFavorite = mutableStateOf(false)
    val isAnimeFavorite: State<Boolean> = _isAnimeFavorite

    private val _backgroundColor = mutableStateOf(BlueGray500)
    val backgroundColor: State<Color> = _backgroundColor

    private var currentAnimeId: Int? = null
    var currentAnimeTitle: String? = null

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("animeName")?.let { animeName ->
            if (animeName.isNotEmpty()) {
                currentAnimeTitle = animeName
            }
        }
        savedStateHandle.get<Int>("animeId")?.let { animeId ->
            if (animeId != -1) {
                currentAnimeId = animeId
                onEvent(AnimeDetailEvent.GetAnimeDetail(animeId))
            }
        }
    }

    fun onEvent(event: AnimeDetailEvent) {
        when (event) {
            is AnimeDetailEvent.AddToFavorite -> {
                updateAnimeIsFavorite()
            }
            is AnimeDetailEvent.GetAnimeDetail -> {
                viewModelScope.launch {
                    coroutineScope {
                        launch { getAnimeDetail(event.animeId) }
                        launch { isAnimeFavorite() }
                    }
                }
            }
        }
    }

    private fun updateAnimeIsFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _animeDetail.value?.toAnimeListItemEntry()?.toAniMangaListItemEntity()
                    ?.let {
                        repository.updateAnimeIsFavorite(!_isAnimeFavorite.value, it)
                        _isAnimeFavorite.value = !_isAnimeFavorite.value
                    }
            } catch (e: Exception) {
                _uiEventFlow.emit(UIEvent.ShowSnackbar("An error occurred adding to favorite"))
                Log.e("AnimeDetailViewModel", "updateAnimeIsFavorite: ${e.message}")
            }
        }
    }

    private suspend fun getAnimeDetail(animeId: Int) {
        _uiEventFlow.emit(UIEvent.Loading)
        when (val response = repository.getAnimeDetail(animeId)) {
            is Resource.Success -> {
                _animeDetail.value = response.data
                _uiEventFlow.emit(UIEvent.DataLoaded)
            }
            is Resource.Error -> {
                _uiEventFlow.emit(UIEvent.ShowSnackbar("Error ${response.code}: ${response.message}"))
            }
            else -> {
                _uiEventFlow.emit(UIEvent.ShowSnackbar("An error occurred getting anime detail"))
            }
        }
    }

    private suspend fun isAnimeFavorite() {
        try {
            currentAnimeId?.let { repository.getAnimeFromCache(it).flowOn(Dispatchers.IO).collect{ item ->
                _isAnimeFavorite.value =  item?.isFavorite ?: false
            }}
        } catch (e: Exception) {
            _uiEventFlow.emit(UIEvent.ShowSnackbar("An error occurred getting anime favorite"))
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object Loading : UIEvent()
        object DataLoaded : UIEvent()
    }
}