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
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentCalenderEventDialogBinding
import com.buzztech.calenderapp.databinding.FragmentTreeofLifeDialogBinding
import com.buzztech.calenderapp.utils.postDelayed

class CalenderEventDialogFragment : DialogFragment() {

    private var _binding : FragmentCalenderEventDialogBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderEventDialogBinding.inflate(layoutInflater)

        binding.apply {
            postDelayed(300){
                mainText.visibility = View.VISIBLE
                goldNotePlate.visibility = View.VISIBLE
            }
            goldClose.setOnClickListener {
                dismiss()
            }
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