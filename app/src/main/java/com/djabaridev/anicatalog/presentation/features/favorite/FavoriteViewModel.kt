package com.djabaridev.anicatalog.presentation.features.favorite

import android.util.Log
import androidx.compose.foundation.gestures.DraggableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.mapper.toAnimeListEntities
import com.djabaridev.anicatalog.domain.mapper.toMangaListEntities
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: AniCatalogRepository,
) : ViewModel() {
    private val _tabIndex: MutableLiveData<Int> = MutableLiveData(0)
    val tabIndex: LiveData<Int> = _tabIndex
    val tabs = listOf("Anime", "Manga")

    val favoriteAnimeList : LiveData<List<AniMangaListItemEntity>> = repository.getFavoriteAnimeList().map {
        it.toAnimeListEntities()
    }.asLiveData()

    val favoriteMangaList : LiveData<List<AniMangaListItemEntity>> = repository.getFavoriteMangaList().map {
        it.toMangaListEntities()
    }.asLiveData()

    var isSwipeToLeft: Boolean = false
    private val draggableState = DraggableState {
        isSwipeToLeft = it > 0
    }

    private val _dragState : MutableLiveData<DraggableState> = MutableLiveData(draggableState)
    val dragState : LiveData<DraggableState> = _dragState

    init {
        Log.d("Favorite ViewModel", "init")
    }

    fun updateTabIndexOnSwipe() {
        _tabIndex.value = when (isSwipeToLeft) {
            true -> {
                _tabIndex.value?.plus(1)?.let { Math.floorMod(it, tabs.size) }
            }
            false -> {
                _tabIndex.value?.minus(1)?.let { Math.floorMod(it, tabs.size) }
            }
        }
    }

    fun updateTabIndex(index: Int) {
        _tabIndex.value = index
    }
}