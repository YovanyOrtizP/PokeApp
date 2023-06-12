package com.yop.pokeyovany.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.squareup.picasso.Picasso
import com.yop.pokeyovany.R
import com.yop.pokeyovany.data.model.detailinf.PokemonDetailResponse
import com.yop.pokeyovany.data.model.general.Result
import com.yop.pokeyovany.databinding.FragmentPokemonDetailBinding
import com.yop.pokeyovany.usecases.PokemonUseCases
import com.yop.pokeyovany.util.UIState
import com.yop.pokeyovany.viewmodel.PokemonsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)


        viewModel.pokemonDetails.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> {}
                is UIState.Success<PokemonDetailResponse> -> {
                    initViews(it.response)
                }

                is UIState.Error -> {}
            }
        }

        viewModel.flowPokemonsDetails()
        return binding.root
    }

    private fun initViews(response: PokemonDetailResponse) {
        response?.let {
            Picasso.get().load(it.sprites?.frontDefault).resize(1000,900).into(binding.ivCharacter)
            binding.tvCharacterName.text = it.name
            binding.tvCharacterHeight.text = it.height.toString()
            binding.tvCharacterOrder.text = it.order.toString()
            binding.tvCharacterWeight.text = it.weight.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}