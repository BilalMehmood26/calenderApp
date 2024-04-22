package com.buzztech.calenderapp.ui.component

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBindings
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.DialogFragmentCityOfAdamBinding
import com.buzztech.calenderapp.databinding.DialogLetterStyleBinding
import com.buzztech.calenderapp.databinding.FragmentCityofAdamBinding
import com.buzztech.calenderapp.model.CityOfAdam
import com.buzztech.calenderapp.utils.postDelayed

class CityOfAdamDialogFragment(val cityOfAdamData: ArrayList<CityOfAdam>) : DialogFragment() {

    private var _binding: DialogLetterStyleBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLetterStyleBinding.inflate(layoutInflater)

        binding.apply {
            postDelayed(200) {
                group.visibility = View.VISIBLE
                goldNotePlate.visibility = View.VISIBLE
            }
            cursedWindTv.text = cityOfAdamData[0].cursed
            constellationTv.text = cityOfAdamData[0].contstellation
            monthNameTv.text = cityOfAdamData[0].monthName
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