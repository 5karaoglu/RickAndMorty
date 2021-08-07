package com.besirkaraoglu.rickandmorty.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.besirkaraoglu.rickandmorty.R
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Character
import com.besirkaraoglu.rickandmorty.databinding.CharacterSingleItemBinding
import com.besirkaraoglu.rickandmorty.util.BaseViewHolder
import com.squareup.picasso.Picasso

class MainAdapter(
    private val clickListener : ClickListener
            ): PagingDataAdapter<Character, MainAdapter.CharacterViewHolder>(CharacterComparator){
    interface ClickListener{
        fun itemClicked(item: Character)
    }

    inner class CharacterViewHolder(
        private val binding: CharacterSingleItemBinding
    ): BaseViewHolder<Character>(binding.root) {
        override fun bind(item: Character) {
           with(binding){
               Picasso.get()
                   .load(item.image)
                   .error(R.drawable.ic_launcher_foreground)
                   .fit().centerInside()
                   .into(iv)

               tv.text = item.name

               binding.root.setOnClickListener {
                   clickListener.itemClicked(item)
               }
           }
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterSingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CharacterViewHolder(binding)
    }
}

object CharacterComparator : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character) =
        oldItem == newItem
}