package com.buzztech.calenderapp.ui.main.knowledge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buzztech.calenderapp.databinding.ItemStringBlueBinding

class KnowledgeContentAdapter(val contentList: List<String>,val onItemClick : (position:Int) -> Unit) : RecyclerView.Adapter<KnowledgeContentAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemStringBlueBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStringBlueBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = contentList[position]

        holder.binding.contentItem.text = content
        holder.binding.contentItem.setOnClickListener {
            onItemClick.invoke(position)
        }
    }
}