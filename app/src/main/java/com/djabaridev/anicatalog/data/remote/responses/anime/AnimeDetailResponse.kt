package com.djabaridev.anicatalog.data.remote.responses.anime

import com.djabaridev.anicatalog.data.remote.responses.animanga.AlternativeTitles
import com.djabaridev.anicatalog.data.remote.responses.animanga.Genre
import com.djabaridev.anicatalog.data.remote.responses.animanga.MainPicture
import com.djabaridev.anicatalog.data.remote.responses.animanga.Picture

data class AnimeDetailResponse(
    val alternative_titles: AlternativeTitles,
    val average_episode_duration: Int,
    val background: String,
    val broadcast: Broadcast,
    val created_at: String,
    val end_date: String,
    val genres: List<Genre>,
    val id: Int,
    val main_picture: MainPicture,
    val mean: Double,
    val media_type: String,
    val my_list_status: MyListStatus,
    val nsfw: String,
    val num_episodes: Int,
    val num_list_users: Int,
    val num_scoring_users: Int,
    val pictures: List<Picture>,
    val popularity: Int,
    val rank: Int,
    val rating: String,
    val recommendations: List<Recommendation>,
    val related_anime: List<RelatedAnime>,
    val related_manga: List<Any>,
    val source: String,
    val start_date: String,
    val start_season: StartSeason,
    val statistics: Statistics,
    val status: String,
    val studios: List<Studio>,
    val synopsis: String,
    val title: String,
    val updated_at: String
)