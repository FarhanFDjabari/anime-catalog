package com.djabaridev.anicatalog.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class MangaListItemEntry(
    @PrimaryKey
    val id: Int,
    val largePicture: String,
    val mediumPicture: String,
    val title: String,
    val jpTitle: String,
    val numChapters: Int,
    val numVolumes: Int,
    val status: String,
    val synopsis: String,
    val mean: Double,
    val genres: List<String>,
    val authors: String,
    val isFavorite: Boolean = false,
)