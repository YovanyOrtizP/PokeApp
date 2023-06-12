package com.yop.pokeyovany.data.repository

import com.yop.pokeyovany.data.model.detailinf.PokemonDetailResponse
import com.yop.pokeyovany.data.model.general.PokemonResponse
import com.yop.pokeyovany.data.remote.PokemonApi
import retrofit2.Response
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi
) : PokemonRepository {

    override suspend fun getPokemonFlow(offset: Int?): Response<PokemonResponse> =
        pokemonApi.getPokemons(offset)

    override suspend fun getPokemonDetails(pokemonId: String): Response<PokemonDetailResponse> =
        pokemonApi.getPokemonDetails(pokemonId)

}