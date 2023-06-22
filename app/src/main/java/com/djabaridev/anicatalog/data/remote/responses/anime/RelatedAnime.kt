package com.djabaridev.anicatalog.data.remote.responses.anime

data class RelatedAnime(
    val node: Node,
    val relation_type: String,
    val relation_type_formatted: String
)