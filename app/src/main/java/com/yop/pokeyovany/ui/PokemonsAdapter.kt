package com.yop.pokeyovany.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yop.pokeyovany.data.model.general.Result
import com.yop.pokeyovany.databinding.ListItemBinding

class PokemonsAdapter(
    private val pokeList: MutableList<Result> = mutableListOf(),
    private val clickListener: (Result) -> Unit
) : RecyclerView.Adapter<PokemonsAdapter.ViewHolder>(){

    fun updatePokemonAdapter(newPokemon: List<Result>){
        pokeList.addAll(newPokemon)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val items: ListItemBinding) :
        RecyclerView.ViewHolder(items.root) {
        fun pokemonList(resultResponse: Result) {
            items.tvName.text = resultResponse.name
            itemView.setOnClickListener{clickListener(resultResponse)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonsAdapter.ViewHolder = ViewHolder(
        ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PokemonsAdapter.ViewHolder, position: Int) {
        holder.pokemonList(pokeList[position])
    }

    override fun getItemCount(): Int = pokeList.size
}