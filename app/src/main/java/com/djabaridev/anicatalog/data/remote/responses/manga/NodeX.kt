package com.djabaridev.anicatalog.data.remote.responses.manga

import com.djabaridev.anicatalog.data.remote.responses.animanga.MainPicture

data class NodeX(
    val id: Int,
    val main_picture: MainPicture,
    val title: String
)