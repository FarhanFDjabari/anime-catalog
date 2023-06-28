package com.djabaridev.anicatalog.presentation.features.detail

sealed class MangaDetailEvent {
    data class GetMangaDetail(val mangaId: Int): MangaDetailEvent()
    object AddToFavorite: MangaDetailEvent()
}
