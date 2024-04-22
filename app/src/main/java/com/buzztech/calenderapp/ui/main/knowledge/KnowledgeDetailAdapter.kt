package com.buzztech.calenderapp.ui.main.knowledge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buzztech.calenderapp.databinding.ItemKnowledgeBinding
import com.buzztech.calenderapp.model.Knowledge

class KnowledgeDetailAdapter(val knowledgeList: Knowledge) :
    RecyclerView.Adapter<KnowledgeDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemKnowledgeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemKnowledgeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return knowledgeList.header.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = knowledgeList.type[position]

        if(model.contains("data")){
            holder.binding.apply {
                if(model.contains("CONTENTS")){
                    detailsTv.visibility = View.GONE
                    headerTv.visibility = View.GONE
                    knowledgeIv.visibility = View.GONE
                }else{
                    detailsTv.visibility = View.VISIBLE
                    knowledgeIv.visibility = View.GONE
                    headerTv.visibility = View.VISIBLE
                    headerTv.text = knowledgeList.header[position]
                    detailsTv.text = knowledgeList.data.get(position.toString())
                }
            }
        }else{
            holder.binding.headerTv.text = knowledgeList.header[position]
            holder.binding.detailsTv.visibility = View.GONE
            holder.binding.knowledgeIv.visibility = View.VISIBLE
        }
    }
}