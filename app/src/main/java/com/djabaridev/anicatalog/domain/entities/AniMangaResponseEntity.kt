package com.djabaridev.anicatalog.domain.entities

import com.djabaridev.anicatalog.data.remote.responses.animanga.Paging
import com.djabaridev.anicatalog.data.remote.responses.anime.Season

data class AniMangaResponseEntity(
    val animangas: List<AniMangaListItemEntity>,
    val paging: Paging? = null,
    val season: Season? = null,
)