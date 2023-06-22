package com.djabaridev.anicatalog.domain.usecases

import com.djabaridev.anicatalog.domain.entities.AniMangaResponseEntity
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import javax.inject.Inject

class GetMangaList @Inject constructor(
    private val aniCatalogRepository: AniCatalogRepository
) {
    suspend fun execute(keyword: String, limit: Int = 10, offset: Int = 0): Resource<AniMangaResponseEntity> {
        return aniCatalogRepository.getMangaList(keyword, limit, offset)
    }
}