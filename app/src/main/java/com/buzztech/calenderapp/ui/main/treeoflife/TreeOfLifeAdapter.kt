package com.buzztech.calenderapp.ui.main.treeoflife

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buzztech.calenderapp.databinding.ItemStringListBinding

class TreeOfLifeAdapter(val list: List<String>) :
    RecyclerView.Adapter<TreeOfLifeAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemStringListBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStringListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model = list?.get(position)
            holder.binding.contentItem.text  = model
    }
}