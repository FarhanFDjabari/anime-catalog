package com.djabaridev.anicatalog.presentation.features.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaDetailResponse
import com.djabaridev.anicatalog.domain.mapper.toAniMangaListItemEntity
import com.djabaridev.anicatalog.domain.mapper.toAnimeListItemEntry
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.presentation.theme.BlueGray500
import com.djabaridev.anicatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
    private val repository: AniCatalogRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _mangaDetail = mutableStateOf<MangaDetailResponse?>(null)
    val mangaDetail: State<MangaDetailResponse?> = _mangaDetail

    private val _isMangaFavorite = mutableStateOf(false)
    val isMangaFavorite: State<Boolean> = _isMangaFavorite

    private val _backgroundColor = mutableStateOf(BlueGray500)
    val backgroundColor: State<Color> = _backgroundColor

    private var currentMangaId: Int? = null
    var currentMangaTitle: String? = null

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("mangaName")?.let { mangaName ->
            if (mangaName.isNotEmpty()) {
                currentMangaTitle = mangaName
            }
        }
        savedStateHandle.get<Int>("mangaId")?.let { mangaId ->
            if (mangaId != -1) {
                currentMangaId = mangaId
//                onEvent(MangaDetailEvent.GetMangaDetail(mangaId))
            }
        }
    }

    fun onEvent(event: MangaDetailEvent) {
        when (event) {
            is MangaDetailEvent.AddToFavorite -> {
                updateMangaIsFavorite()
            }
            is MangaDetailEvent.GetMangaDetail -> {
                getMangaDetail(event.mangaId)
                isMangaFavorite()
            }
        }
    }

    private fun updateMangaIsFavorite() {
        viewModelScope.launch {
            try {
                _mangaDetail.value?.toAnimeListItemEntry()?.toAniMangaListItemEntity()
                    ?.let { repository.updateMangaIsFavorite(!_isMangaFavorite.value, it) }
            } catch (e: Exception) {
                _uiEventFlow.emit(UIEvent.ShowSnackbar("An error occurred adding to favorite"))
            }
        }
    }

    private fun getMangaDetail(mangaId: Int) {
        viewModelScope.launch {
            _uiEventFlow.emit(UIEvent.Loading)
            val response = repository.getMangaDetail(mangaId)
            if (response is Resource.Success) {
                _mangaDetail.value = response.data
                _uiEventFlow.emit(UIEvent.DataLoaded)
            } else {
                _uiEventFlow.emit(UIEvent.ShowSnackbar("Error getting anime detail"))
            }
        }
    }

    private fun isMangaFavorite() {
        viewModelScope.launch {
            try {
                val cache = currentMangaId?.let { repository.getMangaFromCache(it).flowOn(Dispatchers.IO).asLiveData()}
                _isMangaFavorite.value =  cache?.value?.isFavorite ?: false
            } catch (e: Exception) {
                _uiEventFlow.emit(UIEvent.ShowSnackbar("An error occurred getting anime favorite"))
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object Loading : UIEvent()
        object DataLoaded : UIEvent()
    }
}