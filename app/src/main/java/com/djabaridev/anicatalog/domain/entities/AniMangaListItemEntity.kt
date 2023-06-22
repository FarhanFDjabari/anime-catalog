package com.djabaridev.anicatalog.domain.entities

data class AniMangaListItemEntity(
    val id: Int,
    val largePicture: String,
    val mediumPicture: String,
    val title: String,
    val jpTitle: String,
    val averageEpisodeDuration: Int,
    val mean: Double,
    val numEpisodes: Int,
    val status: String,
    val synopsis: String,
    val genres: List<String>,
    val numChapters: Int,
    val numVolumes: Int,
    val authors: String,
    val isFavorite: Boolean,
)