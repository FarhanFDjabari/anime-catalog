package com.djabaridev.anicatalog.data.remote.responses.anime

import com.djabaridev.anicatalog.data.remote.responses.animanga.MainPicture

data class Node(
    val id: Int,
    val main_picture: MainPicture,
    val title: String
)