package com.djabaridev.anicatalog.data.remote.responses.animanga

import com.djabaridev.anicatalog.data.remote.responses.anime.Season

data class AniMangaListItemResponse(
    val `data`: List<Data>,
    val paging: Paging? = null,
    val season: Season? = null,
)