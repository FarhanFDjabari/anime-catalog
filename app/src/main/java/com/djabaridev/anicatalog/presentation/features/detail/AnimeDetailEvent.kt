package com.djabaridev.anicatalog.presentation.features.detail

sealed class AnimeDetailEvent {
    data class GetAnimeDetail(val animeId: Int): AnimeDetailEvent()
    object AddToFavorite: AnimeDetailEvent()
}
