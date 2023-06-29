package com.djabaridev.anicatalog.presentation.features.search

sealed class SearchEvent {
    data class SearchAniManga(val keyword: String): SearchEvent()
}