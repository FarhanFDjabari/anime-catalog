package com.djabaridev.anicatalog.domain.usecases

import com.djabaridev.anicatalog.data.remote.responses.manga.MangaDetailResponse
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import javax.inject.Inject

class GetMangaDetail @Inject constructor(
    private val aniCatalogRepository: AniCatalogRepository
) {
    suspend fun execute(mangaId: Int): Resource<MangaDetailResponse> {
        return aniCatalogRepository.getMangaDetail(mangaId)
    }
}