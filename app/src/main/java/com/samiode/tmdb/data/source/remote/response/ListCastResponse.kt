package com.samiode.tmdb.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListCastResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("cast")
    val cast: List<CastResponse>
)
