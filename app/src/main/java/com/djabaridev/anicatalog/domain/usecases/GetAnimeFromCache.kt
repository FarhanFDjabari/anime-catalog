package com.djabaridev.anicatalog.domain.usecases

import com.djabaridev.anicatalog.data.local.models.AnimeListItemEntry
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeDetailResponse
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeFromCache @Inject constructor(
    private val aniCatalogRepository: AniCatalogRepository
) {
    fun execute(animeId: Int): Flow<AnimeListItemEntry?> {
        return aniCatalogRepository.getAnimeFromCache(animeId)
    }
}