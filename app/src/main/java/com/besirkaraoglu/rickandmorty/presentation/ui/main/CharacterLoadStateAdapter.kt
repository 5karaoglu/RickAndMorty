package com.besirkaraoglu.rickandmorty.presentation.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.besirkaraoglu.rickandmorty.R
import com.besirkaraoglu.rickandmorty.databinding.FooterItemLayoutBinding
import com.besirkaraoglu.rickandmorty.databinding.PagingShimmerBinding
import com.facebook.shimmer.ShimmerFrameLayout


class CharacterLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharacterLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)


    class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.footer_item_layout, parent, false)
    ) {
        private val binding = FooterItemLayoutBinding.bind(itemView)
        private val loading: PagingShimmerBinding = binding.loading
        private val errorMsg: TextView = binding.tv
        private val retry: Button = binding.button
            .also {
                it.setOnClickListener { retry() }
            }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }

            loading.root.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState !is LoadState.Loading
            errorMsg.isVisible = loadState !is LoadState.Loading
        }
    }
}
