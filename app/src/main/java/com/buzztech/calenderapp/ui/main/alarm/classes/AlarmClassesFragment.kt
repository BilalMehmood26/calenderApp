package com.buzztech.calenderapp.ui.main.alarm.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentAlarmClassesBinding
import com.buzztech.calenderapp.databinding.FragmentSolarPartsBinding
import com.buzztech.calenderapp.ui.component.SimpleTextDialogFragment
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible

class AlarmClassesFragment : Fragment() {
    private var _binding: FragmentAlarmClassesBinding? = null
    private val binding get() = _binding!!

    private var dialogData :String? = ""
    private lateinit var textDialogFragment: SimpleTextDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmClassesBinding.inflate(layoutInflater)
        binding.apply {
            cityOfAdamIv.setOnClickListener {
                textDialogFragment = SimpleTextDialogFragment(data = dialogData!!)
                textDialogFragment.show(childFragmentManager,"Text Dialog")
            }

            icBack.setOnClickListener {
                findNavController().popBackStack()
            }

            requireActivity().findViewById<ImageView>(R.id.drawer).gone()
        }
        return binding.root
    }
}