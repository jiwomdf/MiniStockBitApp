package com.katilijiwo.ministockbitapp.ui.watchlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.katilijiwo.ministockbitapp.databinding.LayoutLoadStateBinding

class WatchListLoadStateAdapter(
    private val retry: () -> Unit,
    private val showToast: (loadState: LoadState) -> Unit
) : LoadStateAdapter<WatchListLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class LoadStateViewHolder(private val binding: LayoutLoadStateBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState) {
            val progress = binding.loadStateProgress
            val btnRetry = binding.loadStateRetry

            btnRetry.isVisible = loadState !is LoadState.Loading
            progress.isVisible = loadState is LoadState.Loading
            if (loadState is LoadState.Error){
                showToast.invoke(loadState)
            }
            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}