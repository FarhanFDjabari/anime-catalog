package com.djabaridev.anicatalog.domain.usecases

import com.djabaridev.anicatalog.data.local.models.AnimeListItemEntry
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeDetailResponse
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateMangaIsFavorite @Inject constructor(
    private val aniCatalogRepository: AniCatalogRepository
) {
    suspend fun execute(isFavorite: Boolean, manga: AniMangaListItemEntity) {
        return aniCatalogRepository.updateMangaIsFavorite(isFavorite, manga)
    }
}