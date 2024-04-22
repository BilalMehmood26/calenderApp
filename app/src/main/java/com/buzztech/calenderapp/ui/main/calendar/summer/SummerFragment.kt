package com.buzztech.calenderapp.ui.main.calendar.summer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentSummerBinding
import com.buzztech.calenderapp.model.Months
import com.buzztech.calenderapp.ui.main.calendar.CalenderAdapter
import com.buzztech.calenderapp.ui.main.calendar.CalenderViewModel
import kotlinx.coroutines.launch

class SummerFragment(val list: ArrayList<Months>) : Fragment() {

    private var _binding: FragmentSummerBinding? = null
    private val binding get() = _binding!!

    private lateinit var calenderAdapter: CalenderAdapter
    private lateinit var viewModel: CalenderViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSummerBinding.inflate(layoutInflater)
        binding.apply {
            summerMonths(list)
        }
        return binding.root
    }

    private fun summerMonths(summerList: ArrayList<Months>) {
        summerList.sortBy { it.monthNo }
        calenderAdapter = CalenderAdapter(summerList,requireContext()) {
            val bundle = Bundle()
            bundle.putString("image",it.image)
            bundle.putLong("month",it.monthNo)
            findNavController().navigate(R.id.action_calenderFragment_to_calenderDetailsFragment,bundle)
        }
        binding.apply {
            summerListRv.apply {
                adapter = calenderAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }
}