package com.djabaridev.anicatalog.data.remote.responses.anime

data class Recommendation(
    val node: Node,
    val num_recommendations: Int
)