package com.besirkaraoglu.rickandmorty.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.besirkaraoglu.rickandmorty.databinding.FooterItemLayoutBinding
import com.besirkaraoglu.rickandmorty.util.BaseViewHolder
import com.besirkaraoglu.rickandmorty.util.showSnackBar


class CharacterLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(FooterItemLayoutBinding.inflate(LayoutInflater.from(parent.context)),parent.context, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}
class LoadStateViewHolder(
    private val binding: FooterItemLayoutBinding,
    private val context: Context,
    private val retry: () -> Unit
) : BaseViewHolder<LoadState>(binding.root) {
    override fun bind(item: LoadState) {
        if (item is LoadState.Error){
            context.showSnackBar(binding.root,"Error: ${item.error.message}")
        }
        with(binding){
            pb.isVisible = item is LoadState.Loading
            button.isVisible = item is LoadState.Error
            button.setOnClickListener {
                retry
            }
        }
    }


}