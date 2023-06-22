package com.djabaridev.anicatalog.domain.usecases

import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeDetailResponse
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import javax.inject.Inject

class GetAnimeDetail @Inject constructor(
    private val aniCatalogRepository: AniCatalogRepository
) {
    suspend fun execute(animeId: Int): Resource<AnimeDetailResponse> {
        return aniCatalogRepository.getAnimeDetail(animeId)
    }
}