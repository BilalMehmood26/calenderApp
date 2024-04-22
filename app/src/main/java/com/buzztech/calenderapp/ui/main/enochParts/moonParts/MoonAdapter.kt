package com.buzztech.calenderapp.ui.main.enochParts.moonParts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.ItemFullMoonBinding
import com.buzztech.calenderapp.databinding.ItemStringBlueBinding
import com.buzztech.calenderapp.model.FullMoon
import com.buzztech.calenderapp.ui.component.curveRecycler.RvState

class MoonAdapter(
    val moonList: ArrayList<FullMoon>,
    val rvState: MutableLiveData<RvState>,
    val itemWidth: Int,
    val itemHeight: Int,
    val context: Context,
    val item: (moon: String) -> Unit
) : RecyclerView.Adapter<MoonAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFullMoonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.layoutParams = binding.root.layoutParams.apply {
                width = itemWidth
                height = itemHeight
            }
        }
        fun moon(moonItem: FullMoon) {
            binding.apply {
                moonIv.setImageResource(moonItem.moonImg)
                moonPhaseDayTv.text = moonItem.moonDay

                if(moonItem.moonPhase.contains("waning")){
                    moonPhaseDayTv.setTextColor(ContextCompat.getColor(context, R.color.blue))
                    item.invoke("waning")
                }else if(moonItem.moonPhase.contains("full moon") ){
                    moonPhaseDayTv.setTextColor(ContextCompat.getColor(context, R.color.white))
                    item.invoke("full moon")
                }else if(moonItem.moonPhase.contains("Black Moon") ){
                    moonPhaseDayTv.setTextColor(ContextCompat.getColor(context, R.color.white))
                    item.invoke("Black Moon")
                }else if(moonItem.moonPhase.contains("waxing")){
                    moonPhaseDayTv.setTextColor(ContextCompat.getColor(context, R.color.brown))
                    item.invoke("waxing")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFullMoonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return moonList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.moon(moonList[position])
    }
}