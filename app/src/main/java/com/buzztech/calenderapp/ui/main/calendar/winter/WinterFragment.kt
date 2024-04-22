package com.buzztech.calenderapp.ui.main.calendar.winter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentSummerBinding
import com.buzztech.calenderapp.databinding.FragmentWinterBinding
import com.buzztech.calenderapp.model.Months
import com.buzztech.calenderapp.ui.main.calendar.CalenderAdapter
import com.buzztech.calenderapp.ui.main.calendar.CalenderViewModel

class WinterFragment(val list: ArrayList<Months>) : Fragment() {

    private var _binding: FragmentWinterBinding? = null
    private val binding get() = _binding!!

    private lateinit var calenderAdapter: CalenderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWinterBinding.inflate(layoutInflater)
        binding.apply {
            winterMonths(list)
        }
        return binding.root
    }

    private fun winterMonths(winterList: ArrayList<Months>) {
        winterList.sortBy { it.monthNo }
        calenderAdapter = CalenderAdapter(winterList,requireContext()) {
            val bundle = Bundle()
            bundle.putString("image",it.image)
            bundle.putLong("month",it.monthNo)
            findNavController().navigate(R.id.action_calenderFragment_to_calenderDetailsFragment,bundle)
        }
        binding.apply {
             winterListRv.apply {
                 adapter = calenderAdapter
                 layoutManager = GridLayoutManager(requireContext(), 2)
             }
        }
    }
}