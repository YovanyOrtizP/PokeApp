package com.yop.pokeyovany.data.model.detailinf


import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("sprites")
    val sprites: Sprites?,
    @SerializedName("weight")
    val weight: Int?
)