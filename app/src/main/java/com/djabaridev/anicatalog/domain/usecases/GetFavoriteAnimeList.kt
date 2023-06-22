package com.djabaridev.anicatalog.domain.usecases

import com.djabaridev.anicatalog.data.local.models.AnimeListItemEntry
import com.djabaridev.anicatalog.domain.entities.AniMangaResponseEntity
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteAnimeList @Inject constructor(
    private val aniCatalogRepository: AniCatalogRepository
) {
    fun execute(): Flow<List<AnimeListItemEntry>> {
        return aniCatalogRepository.getFavoriteAnimeList()
    }
}