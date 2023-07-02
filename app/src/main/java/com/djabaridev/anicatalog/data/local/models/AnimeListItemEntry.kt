package com.djabaridev.anicatalog.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeListItemEntry(
    @PrimaryKey
    val id: Int,
    val largePicture: String?,
    val mediumPicture: String?,
    val title: String,
    val jpTitle: String,
    val averageEpisodeDuration: Int,
    val mean: Double,
    val numEpisodes: Int,
    val status: String,
    val synopsis: String,
    val genres: List<String>,
    val isFavorite: Boolean = false,
)