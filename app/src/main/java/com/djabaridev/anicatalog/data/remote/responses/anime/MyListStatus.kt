package com.djabaridev.anicatalog.data.remote.responses.anime

data class MyListStatus(
    val is_rewatching: Boolean,
    val num_episodes_watched: Int,
    val score: Int,
    val status: String,
    val updated_at: String
)