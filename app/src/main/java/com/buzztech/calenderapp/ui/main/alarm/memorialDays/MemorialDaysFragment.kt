package com.buzztech.calenderapp.ui.main.alarm.memorialDays

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentHolyDaysBinding
import com.buzztech.calenderapp.databinding.FragmentMemorialDaysBinding
import com.buzztech.calenderapp.ui.component.SimpleTextDialogFragment
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible

class MemorialDaysFragment : Fragment() {

    companion object {
        fun newInstance() = MemorialDaysFragment()
    }

    private lateinit var viewModel: MemorialDaysViewModel

    private var _binding: FragmentMemorialDaysBinding? = null
    private val binding get() = _binding!!

    private var dialogData :String? = ""
    private lateinit var textDialogFragment: SimpleTextDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemorialDaysBinding.inflate(layoutInflater)
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