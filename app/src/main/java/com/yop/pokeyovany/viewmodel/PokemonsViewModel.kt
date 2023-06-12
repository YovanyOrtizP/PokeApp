package com.yop.pokeyovany.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yop.pokeyovany.data.model.detailinf.PokemonDetailResponse
import com.yop.pokeyovany.data.model.general.Result
import com.yop.pokeyovany.usecases.PokemonDetailsUseCases
import com.yop.pokeyovany.usecases.PokemonUseCases
import com.yop.pokeyovany.usecases.PokemonUseCases.Companion.counter
import com.yop.pokeyovany.util.SelectedPokemonException
import com.yop.pokeyovany.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(
    private val pokemonUseCases: PokemonUseCases,
    private val pokemonDetailsUseCases: PokemonDetailsUseCases,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    var selectedPokemon: Result? = null

    var loadMoreData: Boolean = false
        private set

    private val _pagePokemons: MutableLiveData<UIState<List<Result>>> =
        MutableLiveData(UIState.Loading)
    val pagePokemons: LiveData<UIState<List<Result>>> get() = _pagePokemons

    private val _pokemonDetails: MutableLiveData<UIState<PokemonDetailResponse>> =
        MutableLiveData(UIState.Loading)
    val pokemonDetails: LiveData<UIState<PokemonDetailResponse>> get() = _pokemonDetails

    fun flowPokemons(isLoadingMore: Boolean = false) {
        loadMoreData = isLoadingMore
        viewModelScope.launch(ioDispatcher) {
            pokemonUseCases().collect() {
                loadMoreData = false
                _pagePokemons.postValue(it)
            }
        }
    }

    fun flowPokemonsDetails() {
        selectedPokemon?.let { pokemon ->
            pokemon.url?.let { url ->
                viewModelScope.launch(ioDispatcher) {
                    pokemonDetailsUseCases(url).collect() {
                        _pokemonDetails.postValue(it)
                    }
                }
            } ?: _pokemonDetails.postValue(UIState.Error(SelectedPokemonException()))
        } ?: _pokemonDetails.postValue(UIState.Error(SelectedPokemonException()))

    }
}