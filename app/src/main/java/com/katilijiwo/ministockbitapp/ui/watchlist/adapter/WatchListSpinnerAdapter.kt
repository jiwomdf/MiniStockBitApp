package com.katilijiwo.ministockbitapp.ui.watchlist.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.katilijiwo.ministockbitapp.R
import com.katilijiwo.ministockbitapp.databinding.ListSpinnerItemBinding

class WatchListSpinnerAdapter(
    private val activity: Activity,
    private val layout: Int,
    private val data: List<String>
) : ArrayAdapter<String>(activity, layout, data) {

    private val VIEW = 1
    private val DROPDOWN = 2

    private var selected : Int = -1

    fun setSelected(selected : Int){
        this.selected = selected
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent, VIEW)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent, DROPDOWN)
    }

    private fun getCustomView(position : Int, parent : ViewGroup, type : Int) : View {
        val binding : ListSpinnerItemBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.list_spinner_item, parent, false)

        binding.tvSelectedItem.text = data[position]
        if(type == VIEW){
            binding.icLogo.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_arrow_down_24, null))
        } else {
            binding.icLogo.setImageDrawable(null)
        }

        return binding.root
    }

}