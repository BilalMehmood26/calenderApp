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
import com.buzztech.calenderapp.databinding.DialogFragmentCityOfAdamBinding
import com.buzztech.calenderapp.databinding.FragmentTextDialogBinding
import com.buzztech.calenderapp.model.HighlightsOfMonths
import com.buzztech.calenderapp.utils.postDelayed

class TextDialogFragment(val highlightsOfMonths: HighlightsOfMonths) : DialogFragment() {


    private var _binding: FragmentTextDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTextDialogBinding.inflate(layoutInflater)

        binding.apply {
            postDelayed(300) {
                scrollData.visibility = View.VISIBLE
                goldNotePlate.visibility = View.VISIBLE
            }

            highlightsOfMonths.apply {
                timeMakeTv.text = data.get("Time Markers").toString()
                highHolyDaysTv.text = data.get("High Holy Days").toString()
                memorialDaysTv.text = data.get("Memorial Days").toString()
                constellationTv.text = data.get("Constellation").toString()
                biblicalHistoryTv.text = data.get("Biblical History").toString()
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