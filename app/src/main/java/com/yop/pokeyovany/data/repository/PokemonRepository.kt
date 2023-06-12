package com.yop.pokeyovany.data.repository

import com.yop.pokeyovany.data.model.detailinf.PokemonDetailResponse
import com.yop.pokeyovany.data.model.general.PokemonResponse
import retrofit2.Response

interface PokemonRepository {

    suspend fun getPokemonFlow(offset: Int? = null): Response<PokemonResponse>
    suspend fun getPokemonDetails(pokemonId: String): Response<PokemonDetailResponse>
}