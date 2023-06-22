package com.djabaridev.anicatalog.domain.usecases

import com.djabaridev.anicatalog.data.local.models.MangaListItemEntry
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaDetailResponse
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import com.djabaridev.anicatalog.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMangaFromCache @Inject constructor(
    private val aniCatalogRepository: AniCatalogRepository
) {
    fun execute(mangaId: Int): Flow<MangaListItemEntry?> {
        return aniCatalogRepository.getMangaFromCache(mangaId)
    }
}