package com.djabaridev.anicatalog.data.remote.responses.animanga

import com.djabaridev.anicatalog.data.remote.responses.manga.Author

data class Node(
    val id: Int,
    val main_picture: MainPicture?,
    val title: String,
    val alternative_titles: AlternativeTitles?,
    val average_episode_duration: Int?,
    val genres: List<Genre>?,
    val mean: Double?,
    val num_episodes: Int?,
    val status: String?,
    val synopsis: String?,
    val authors: List<Author>?,
    val num_chapters: Int?,
    val num_volumes: Int?,
)