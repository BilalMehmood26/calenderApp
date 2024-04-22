package com.buzztech.calenderapp.ui.component

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.DialogLetterStyleBinding
import com.buzztech.calenderapp.databinding.FragmentTreeofLifeDialogBinding
import com.buzztech.calenderapp.model.TreeOfLife
import com.buzztech.calenderapp.ui.main.treeoflife.TreeOfLifeAdapter
import com.buzztech.calenderapp.utils.postDelayed

class TreeofLifeDialogFragment(val treeData: List<String>,val month:String,val branch:String) : DialogFragment() {


    private var _binding : FragmentTreeofLifeDialogBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTreeofLifeDialogBinding.inflate(layoutInflater)

        binding.apply {
            postDelayed(300){
                recyclerView.visibility = View.VISIBLE
                branchTv.visibility = View.VISIBLE
                monthTv.visibility = View.VISIBLE
                goldNotePlate.visibility = View.VISIBLE
            }

            branchTv.text = branch
            monthTv.text = month

            goldClose.setOnClickListener {
                dismiss()
            }
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = TreeOfLifeAdapter(treeData)
        }
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

}