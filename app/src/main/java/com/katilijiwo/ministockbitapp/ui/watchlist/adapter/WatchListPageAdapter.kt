package com.katilijiwo.ministockbitapp.ui.watchlist.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.Data
import com.katilijiwo.ministockbitapp.data.remote.websocket.CryptoCompareWebSocket
import com.katilijiwo.ministockbitapp.databinding.ListWatchListDataBinding

class WatchListPageAdapter: PagingDataAdapter<Data, WatchListPageAdapter.WatchListViewHolder>(
    USERS_COMPARATOR
) {
    private val RED_COLOR = "#F95F62"
    private val GREEN_COLOR = "#00aa6b"

    companion object {
        private val USERS_COMPARATOR = object : DiffUtil.ItemCallback<Data>(){
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        val binding = ListWatchListDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    inner class WatchListViewHolder(private val binding: ListWatchListDataBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Data){
            try {
                if(data.coinInfo != null && data.dISPLAY != null && data.dISPLAY.uSD != null){
                    binding.tvCompanyName.text = data.coinInfo.fullName
                    binding.tvStockCode.text = data.coinInfo.name
                    binding.tvLotPrice.text = data.dISPLAY.uSD.pRICE
                    binding.tvAddition.text = data.dISPLAY.uSD.cHANGEPCTHOUR
                    if(data.dISPLAY.uSD.cHANGEPCTHOUR.contains("-")){
                        binding.tvAddition.setTextColor(Color.parseColor(RED_COLOR))
                    } else {
                        binding.tvAddition.setTextColor(Color.parseColor(GREEN_COLOR))
                    }
                }
            }catch (ex: Exception){
                print(ex.message)
            }
        }
    }

}