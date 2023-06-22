package com.djabaridev.anicatalog.data.remote.responses.anime

data class Status(
    val completed: String,
    val dropped: String,
    val on_hold: String,
    val plan_to_watch: String,
    val watching: String
)