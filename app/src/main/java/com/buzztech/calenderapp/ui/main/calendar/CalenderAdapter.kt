package com.buzztech.calenderapp.ui.main.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.buzztech.calenderapp.databinding.ItemMonthBinding
import com.buzztech.calenderapp.model.Months

class CalenderAdapter(
    val list: ArrayList<Months>,
    val context: Context,
    private val itemClick: (Months) -> Unit
) :
    RecyclerView.Adapter<CalenderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindData(item)

    }

    inner class ViewHolder(val binding: ItemMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(value: Months) {
            binding.apply {
                val circularProgressDrawable = CircularProgressDrawable(context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()
                monthImage.load(value.image) {
                    crossfade(enable = true)
                    placeholder(circularProgressDrawable)
                }
                root.setOnClickListener {
                    itemClick.invoke(value)
                }
            }
        }

    }

}