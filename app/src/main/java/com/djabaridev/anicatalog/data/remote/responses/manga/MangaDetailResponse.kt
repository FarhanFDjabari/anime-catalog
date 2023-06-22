package com.djabaridev.anicatalog.data.remote.responses.manga

import com.djabaridev.anicatalog.data.remote.responses.animanga.AlternativeTitles
import com.djabaridev.anicatalog.data.remote.responses.animanga.Genre
import com.djabaridev.anicatalog.data.remote.responses.animanga.MainPicture
import com.djabaridev.anicatalog.data.remote.responses.animanga.Picture

data class MangaDetailResponse(
    val alternative_titles: AlternativeTitles,
    val authors: List<Author>,
    val background: String,
    val created_at: String,
    val genres: List<Genre>,
    val id: Int,
    val main_picture: MainPicture,
    val mean: Double,
    val media_type: String,
    val my_list_status: MyListStatus,
    val nsfw: String,
    val num_chapters: Int,
    val num_list_users: Int,
    val num_scoring_users: Int,
    val num_volumes: Int,
    val pictures: List<Picture>,
    val popularity: Int,
    val rank: Int,
    val recommendations: List<Recommendation>,
    val related_anime: List<Any>,
    val related_manga: List<RelatedManga>,
    val serialization: List<Serialization>,
    val start_date: String,
    val status: String,
    val synopsis: String,
    val title: String,
    val updated_at: String
)