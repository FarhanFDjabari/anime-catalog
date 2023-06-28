package com.djabaridev.anicatalog.presentation.features.favorite

import android.util.Log
import androidx.compose.foundation.gestures.DraggableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: AniCatalogRepository,
) : ViewModel() {
    private val _tabIndex: MutableLiveData<Int> = MutableLiveData(0)
    val tabIndex: LiveData<Int> = _tabIndex
    val tabs = listOf("Anime", "Manga")

    private val _favoriteAnimeList : MutableLiveData<List<AniMangaListItemEntity>> = MutableLiveData()
    val favoriteAnimeList : LiveData<List<AniMangaListItemEntity>> = _favoriteAnimeList
    private val _favoriteMangaList : MutableLiveData<List<AniMangaListItemEntity>> = MutableLiveData()
    val favoriteMangaList : LiveData<List<AniMangaListItemEntity>> = _favoriteMangaList

    var isSwipeToLeft: Boolean = false
    private val draggableState = DraggableState {
        isSwipeToLeft = it > 0
    }

    private val _dragState : MutableLiveData<DraggableState> = MutableLiveData(draggableState)
    val dragState : LiveData<DraggableState> = _dragState

    init {
        Log.d("Favorite ViewModel", "init")
        getFavoriteAnime()
        getFavoriteManga()
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

    private fun getFavoriteAnime() {
        _favoriteAnimeList.value = List(5) {
            AniMangaListItemEntity(
                id = it,
                title = "Anime $it",
                isFavorite = false,
                genres = listOf("Action", "Adventure"),
                numEpisodes = 5,
                jpTitle = "Shingeki no Kyojin",
                largePicture =
                "https://cdn.myanimelist.net/images/anime/10/47347.jpg",
                mediumPicture =
                "https://cdn.myanimelist.net/images/anime/10/47347.jpg",
                mean = 7.5,
                synopsis = "Synopsis $it",
                status = "Finished Airing",
                averageEpisodeDuration = 24,
                authors = "",
                numChapters = 0,
                numVolumes = 0,
            )
        }
    }

    private fun getFavoriteManga() {
        _favoriteMangaList.value = List(5) {
            AniMangaListItemEntity(
                id = it,
                title = "Manga $it",
                isFavorite = false,
                genres = listOf("Action", "Adventure"),
                numEpisodes = 0,
                jpTitle = "",
                largePicture =
                "https://cdn.myanimelist.net/images/anime/10/47347.jpg",
                mediumPicture =
                "https://cdn.myanimelist.net/images/anime/10/47347.jpg",
                mean = 7.5,
                synopsis = "Synopsis $it",
                status = "Completed",
                averageEpisodeDuration = 0,
                authors = "Hajime Isayama",
                numChapters = 190,
                numVolumes = 10,
            )
        }
    }
}