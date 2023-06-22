package com.djabaridev.anicatalog.domain.usecases

import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeRankingEnum
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaRankingEnum
import com.djabaridev.anicatalog.domain.entities.AniMangaResponseEntity
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import javax.inject.Inject

class GetAnimeRanking @Inject constructor(
    private val aniCatalogRepository: AniCatalogRepository
) {
    suspend fun execute(rankingType: AnimeRankingEnum, limit: Int = 10, offset: Int = 0): Resource<AniMangaResponseEntity> {
        return aniCatalogRepository.getAnimeRanking(rankingType, limit, offset)
    }
}