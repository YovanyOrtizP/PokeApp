package com.yop.pokeyovany.usecases

import com.yop.pokeyovany.data.remote.PokemonApi.Companion.LIMIT
import com.yop.pokeyovany.data.model.general.Result
import com.yop.pokeyovany.data.repository.PokemonRepository
import com.yop.pokeyovany.util.InformationException
import com.yop.pokeyovany.util.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonUseCases @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    operator fun invoke(): Flow<UIState<List<Result>>> = flow {
        emit(UIState.Loading)

        try {
            val response = pokemonRepository.getPokemonFlow(if (counter != 0 && counter <= TOTAL_PAGES) LIMIT * counter else null)
            if(response.isSuccessful){
                response.body()?.let {
                    it.results?.let { data->
                        emit(UIState.Success(data))
                        //counter++
                    }?: throw InformationException("EMPTY LIST")
                } ?: throw InformationException()
            } else{
                throw Exception(response.errorBody()?.string())
            }
        } catch (e: Exception) {
            emit(UIState.Error(e))
        }
    }

    companion object {
        var counter = 0
        private const val TOTAL_PAGES = 1280
    }
}