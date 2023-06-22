package com.djabaridev.anicatalog.data.remote.responses.manga

data class RelatedManga(
    val node: NodeX,
    val relation_type: String,
    val relation_type_formatted: String
)