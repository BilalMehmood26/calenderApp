package com.buzztech.calenderapp.ui.main.calendar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentCalenderBinding
import com.buzztech.calenderapp.model.Months
import com.buzztech.calenderapp.ui.main.calendar.summer.SummerFragment
import com.buzztech.calenderapp.ui.main.calendar.winter.WinterFragment
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible
import kotlinx.coroutines.launch

class CalenderFragment : Fragment() {

    companion object {
        fun newInstance() = CalenderFragment()
    }


    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CalenderViewModel

    private var summerList: ArrayList<Months> = ArrayList()
    private var winterList: ArrayList<Months> = ArrayList()
    private var fragmentList: ArrayList<Fragment> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(CalenderViewModel::class.java)

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().findViewById<ImageView>(R.id.drawer).gone()

        observer()
        return binding.root
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.calenderChannels.collect { event ->
                binding.loader.root.visible()
                when (event) {
                    is CalenderViewModel.CalenderEvent.Error -> {
                        binding.loader.root.gone()
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }

                    is CalenderViewModel.CalenderEvent.SummerMonthList -> {

                        event.summerList.forEach { month ->
                            if (month.monthNo in 1..6) {
                                summerList.add(month)
                                Log.d("monthList", "getMonths Summer: ${month.monthNo}")
                            } else {
                                winterList.add(month)
                                Log.d("monthList", "getMonths Winter: ${month.monthNo}")
                            }
                        }

                        if (!summerList.isEmpty()) {
                            fragmentList = arrayListOf(
                                SummerFragment(summerList),
                                WinterFragment(winterList)
                            )
                        }
                        binding.apply {
                            binding.loader.root.gone()
                            val viewPagerAdapter =
                                ViewPagerAdapter(
                                    fragmentList,
                                    requireActivity().supportFragmentManager,
                                    lifecycle
                                )
                            viewPager.adapter = viewPagerAdapter
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.apply {

            val viewPagerAdapter =
                ViewPagerAdapter(
                    fragmentList,
                    requireActivity().supportFragmentManager,
                    lifecycle
                )
            viewPager.adapter = viewPagerAdapter
        }
    }
}