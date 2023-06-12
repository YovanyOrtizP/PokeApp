package com.yop.pokeyovany.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yop.pokeyovany.R
import com.yop.pokeyovany.data.model.general.Result
import com.yop.pokeyovany.databinding.FragmentPokemonListBinding
import com.yop.pokeyovany.usecases.PokemonUseCases.Companion.counter
import com.yop.pokeyovany.util.UIState
import com.yop.pokeyovany.viewmodel.PokemonsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class PokemonListFragment : Fragment() {
    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonsViewModel by activityViewModels()

    private val mAdapter by lazy {
        PokemonsAdapter{
            viewModel.selectedPokemon = it
            findNavController().navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater,container,false)

        binding.rvPokemonList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastItemsVisible = layoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + pastItemsVisible >= totalItemCount) {
                        loadMoreItems()
                    }
                }
            })
        }

        viewModel.pagePokemons.observe(viewLifecycleOwner){
            when (it){
                is UIState.Loading -> {}
                is UIState.Success<List<Result>> ->{
                    initViews(it.response)
                }
                is UIState.Error -> {}
            }
        }

        viewModel.flowPokemons(false)

        return binding.root
    }

    private fun initViews(response: List<Result>) {
        response.let {
            mAdapter.updatePokemonAdapter(it)
        }
    }


    private fun loadMoreItems() {
        counter++
        viewModel.flowPokemons(true)
    }
}