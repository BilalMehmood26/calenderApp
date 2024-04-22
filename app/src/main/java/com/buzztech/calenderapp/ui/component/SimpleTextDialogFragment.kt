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
import com.buzztech.calenderapp.databinding.FragmentCycleOfYearBinding
import com.buzztech.calenderapp.databinding.FragmentSimpleTextDialogBinding
import com.buzztech.calenderapp.utils.postDelayed

class SimpleTextDialogFragment(val data:String) : DialogFragment() {

    private var _binding: FragmentSimpleTextDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimpleTextDialogBinding.inflate(layoutInflater)

        binding.apply {
            postDelayed(200) {
                //textTv.visibility = View.VISIBLE
                goldNotePlate.visibility = View.VISIBLE
            }

            goldClose.setOnClickListener {
            }
            textTv.text = data
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