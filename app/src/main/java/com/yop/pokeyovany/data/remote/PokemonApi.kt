package com.yop.pokeyovany.data.remote

import com.yop.pokeyovany.data.model.detailinf.PokemonDetailResponse
import com.yop.pokeyovany.data.model.general.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    /**
     * Get the list of the pokemons:
     * https://pokeapi.co/api/v2/pokemon/
     * https://pokeapi.co/api/v2/pokemon/?offset=20&limit=20
     *
     * Get the details of each Pokemon:
     * https://pokeapi.co/api/v2/pokemon/1/  (Number will change per Pokemon)
     */

    @GET(POKEMON_END_POINT)
    suspend fun getPokemons(
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int = LIMIT
    ): Response<PokemonResponse>

    @GET(DETAILS_POKEMON)
    suspend fun getPokemonDetails(
        @Path("id") pokemonId: String
    ): Response<PokemonDetailResponse>

    companion object{
        const val BASE_URL = "https://pokeapi.co/api/"
        private const val POKEMON_END_POINT = "v2/pokemon"
        private const val DETAILS_POKEMON = "$POKEMON_END_POINT/{id}"
        const val LIMIT = 20
    }
}