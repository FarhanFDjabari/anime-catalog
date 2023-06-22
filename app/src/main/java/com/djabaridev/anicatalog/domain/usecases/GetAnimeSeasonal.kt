package com.djabaridev.anicatalog.domain.usecases

import com.djabaridev.anicatalog.domain.entities.AniMangaResponseEntity
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import javax.inject.Inject

class GetAnimeSeasonal @Inject constructor(
    private val aniCatalogRepository: AniCatalogRepository
) {
    suspend fun execute(year: Int = 2023, season: String = "summer",limit: Int = 10, offset: Int = 0): Resource<AniMangaResponseEntity> {
        return aniCatalogRepository.getAnimeSeasonal(year, season, limit, offset)
    }
}