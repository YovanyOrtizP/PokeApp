package com.yop.pokeyovany.usecases

import com.yop.pokeyovany.data.model.detailinf.PokemonDetailResponse
import com.yop.pokeyovany.data.repository.PokemonRepository
import com.yop.pokeyovany.util.InformationException
import com.yop.pokeyovany.util.UIState
import com.yop.pokeyovany.util.getPokemonIdFromUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonDetailsUseCases @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {

    //operator invoke es para invocar la funcion sin llamarla esplícitamente.
    operator fun invoke(pokemonDetailsUrl: String): Flow<UIState<PokemonDetailResponse>> = flow {
    emit(UIState.Loading)
        try {
            pokemonDetailsUrl.getPokemonIdFromUrl()?.let {
                val response = pokemonRepository.getPokemonDetails(it)
                if (response.isSuccessful) {
                    response.body()?.let { data->
                        emit(UIState.Success(data))
                    }?: throw InformationException("No info found")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } ?: throw Exception("NO URL PROVIDED")

        } catch (e: Exception) {
            emit(UIState.Error(e))
        }
    }
}