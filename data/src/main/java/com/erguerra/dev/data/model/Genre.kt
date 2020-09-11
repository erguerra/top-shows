package com.erguerra.dev.data.model

import com.erguerra.dev.data_remote.model.RemoteGenre

data class Genre(
    val id: Int,
    val name: String
)

fun RemoteGenre.toGenre() = Genre(this.id, this.name)

