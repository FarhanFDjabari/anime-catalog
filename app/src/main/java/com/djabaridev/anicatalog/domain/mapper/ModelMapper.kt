package com.djabaridev.anicatalog.domain.mapper

import com.djabaridev.anicatalog.data.local.models.AnimeListItemEntry
import com.djabaridev.anicatalog.data.local.models.MangaListItemEntry
import com.djabaridev.anicatalog.data.remote.responses.animanga.AniMangaListItemResponse
import com.djabaridev.anicatalog.data.remote.responses.animanga.Node
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeDetailResponse
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaDetailResponse
import com.djabaridev.anicatalog.data.remote.responses.manga.NodeX
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity

fun Node.toAnimeListItemEntry() : AnimeListItemEntry {
    return AnimeListItemEntry(
        id = this.id,
        title = this.title,
        jpTitle = this.alternative_titles?.ja?: "-",
        largePicture = this.main_picture?.large,
        mediumPicture = this.main_picture?.medium,
        genres = this.genres?.map { it.name }?: emptyList(),
        averageEpisodeDuration = this.average_episode_duration?: 0,
        mean = this.mean?: 0.0,
        numEpisodes = this.num_episodes?: 0,
        status = this.status?: "-",
        synopsis = this.synopsis?:"-",
        isFavorite = false
    )
}

fun Node.toAniMangaListItemEntity() : AniMangaListItemEntity {
    return AniMangaListItemEntity(
        id = this.id,
        title = this.title,
        jpTitle = this.alternative_titles?.ja?: "-",
        largePicture = this.main_picture?.large,
        mediumPicture = this.main_picture?.medium,
        genres = this.genres?.map { it.name }?: emptyList(),
        averageEpisodeDuration = this.average_episode_duration?: 0,
        mean = this.mean?: 0.0,
        numEpisodes = this.num_episodes?: 0,
        status = this.status?: "-",
        synopsis = this.synopsis?:"-",
        authors = "${this.authors?.first()?.node?.first_name} ${this.authors?.first()?.node?.last_name}",
        numChapters = this.num_chapters?: 0,
        numVolumes = this.num_volumes?: 0,
        isFavorite = false
    )
}

fun Node.toMangaListItemEntry() : MangaListItemEntry {
    return MangaListItemEntry(
        id = this.id,
        title = this.title,
        jpTitle = this.alternative_titles?.ja?: "-",
        largePicture = this.main_picture?.large,
        mediumPicture = this.main_picture?.medium,
        genres = this.genres?.map { it.name }?: emptyList(),
        mean = this.mean?: 0.0,
        numChapters = this.num_chapters?: 0,
        numVolumes = this.num_volumes?: 0,
        status = this.status?: "-",
        synopsis = this.synopsis?:"-",
        authors = "${this.authors?.first()?.node?.first_name} ${this.authors?.first()?.node?.last_name}",
        isFavorite = false
    )
}

fun NodeX.toAniMangaListItemEntity() : AniMangaListItemEntity {
    return AniMangaListItemEntity(
        id = this.id,
        title = this.title,
        jpTitle = "-",
        largePicture = this.main_picture?.large,
        mediumPicture = this.main_picture?.medium,
        genres = emptyList(),
        averageEpisodeDuration = 0,
        mean = 0.0,
        numEpisodes = 0,
        status = "-",
        synopsis = "-",
        authors = "-",
        numChapters = 0,
        numVolumes = 0,
        isFavorite = false
    )
}

fun AnimeListItemEntry.toAniMangaListItemEntity() : AniMangaListItemEntity {
    return AniMangaListItemEntity(
        id = this.id,
        title = this.title,
        jpTitle = this.jpTitle,
        largePicture = this.largePicture,
        mediumPicture = this.mediumPicture,
        numChapters = 0,
        numVolumes = 0,
        genres = this.genres,
        averageEpisodeDuration = this.averageEpisodeDuration,
        mean = this.mean,
        numEpisodes = this.numEpisodes,
        status = this.status,
        synopsis = this.synopsis,
        authors = "-",
        isFavorite = this.isFavorite
    )
}

fun MangaListItemEntry.toAniMangaListItemEntity() : AniMangaListItemEntity {
    return AniMangaListItemEntity(
        id = this.id,
        title = this.title,
        jpTitle = this.jpTitle,
        largePicture = this.largePicture,
        mediumPicture = this.mediumPicture,
        numChapters = this.numChapters,
        numVolumes = this.numVolumes,
        genres = this.genres,
        mean = this.mean,
        status = this.status,
        synopsis = this.synopsis,
        authors = this.authors,
        numEpisodes = 0,
        averageEpisodeDuration = 0,
        isFavorite = this.isFavorite
    )
}

fun AniMangaListItemEntity.toAnimeListItemEntry() : AnimeListItemEntry {
    return AnimeListItemEntry(
        id = this.id,
        title = this.title,
        jpTitle = this.jpTitle,
        largePicture = this.largePicture,
        mediumPicture = this.mediumPicture,
        numEpisodes = this.numEpisodes,
        genres = this.genres,
        averageEpisodeDuration = this.averageEpisodeDuration,
        mean = this.mean,
        status = this.status,
        synopsis = this.synopsis,
        isFavorite = this.isFavorite
    )
}

fun AniMangaListItemEntity.toMangaListItemEntry() : MangaListItemEntry {
    return MangaListItemEntry(
        id = this.id,
        title = this.title,
        jpTitle = this.jpTitle,
        largePicture = this.largePicture,
        mediumPicture = this.mediumPicture,
        numChapters = this.numChapters,
        numVolumes = this.numVolumes,
        genres = this.genres,
        mean = this.mean,
        status = this.status,
        synopsis = this.synopsis,
        authors = this.authors,
        isFavorite = this.isFavorite
    )
}

fun AnimeDetailResponse.toAnimeListItemEntry() : AnimeListItemEntry {
    return AnimeListItemEntry(
        id = this.id,
        title = this.title,
        jpTitle = this.alternative_titles.ja,
        largePicture = this.main_picture?.large,
        mediumPicture = this.main_picture?.medium,
        genres = this.genres.map { it.name },
        averageEpisodeDuration = this.average_episode_duration,
        mean = this.mean,
        numEpisodes = this.num_episodes,
        status = this.status,
        synopsis = this.synopsis,
        isFavorite = false
    )
}

fun MangaDetailResponse.toMangaListItemEntry() : MangaListItemEntry {
    return MangaListItemEntry(
        id = this.id,
        title = this.title,
        jpTitle = this.alternative_titles.ja,
        largePicture = this.main_picture?.large,
        mediumPicture = this.main_picture?.medium,
        genres = this.genres.map { it.name },
        mean = this.mean,
        numChapters = this.num_chapters,
        numVolumes = this.num_volumes,
        status = this.status,
        authors = "${this.authors.first().node.first_name} ${this.authors.first().node.last_name}",
        synopsis = this.synopsis,
        isFavorite = false
    )
}

fun List<AnimeListItemEntry>.toAnimeListEntities() : List<AniMangaListItemEntity> {
    return this.map { it.toAniMangaListItemEntity() }
}

fun List<MangaListItemEntry>.toMangaListEntities() : List<AniMangaListItemEntity> {
    return this.map { it.toAniMangaListItemEntity() }
}

fun AniMangaListItemResponse.toAnimeListEntries() : List<AnimeListItemEntry> {
    return this.data.map { it.node.toAnimeListItemEntry().copy(isFavorite = false) }
}

fun AniMangaListItemResponse.toAnimeListEntities() : List<AniMangaListItemEntity> {
    return this.data.map { it.node.toAniMangaListItemEntity() }
}

fun AniMangaListItemResponse.toMangaListEntries() : List<MangaListItemEntry> {
    return this.data.map { it.node.toMangaListItemEntry().copy(isFavorite = false) }
}

fun AniMangaListItemResponse.toMangaListEntities() : List<AniMangaListItemEntity> {
    return this.data.map { it.node.toAniMangaListItemEntity() }
}

