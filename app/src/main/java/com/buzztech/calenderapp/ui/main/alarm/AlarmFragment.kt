package com.buzztech.calenderapp.ui.main.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentAlarmBinding
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.postDelayed
import com.buzztech.calenderapp.utils.visible

class AlarmFragment : Fragment() {

    companion object {
        fun newInstance() = AlarmFragment()
    }

    private var isDown: Boolean = false
    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!
    private var shortAnimationDuration: Int = 10

    private lateinit var viewModel: AlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmBinding.inflate(layoutInflater)
        binding.apply {
            holyBtn.setOnClickListener {
                navigate(R.id.action_alarmFragment_to_holyDaysFragment)
            }
            memorialBtn.setOnClickListener {
                navigate(R.id.action_alarmFragment_to_memorialDaysFragment)
            }
            classesBtn.setOnClickListener {
                navigate(R.id.action_alarmFragment_to_alarmClassesFragment)
            }

            customAlarmBtn.setOnClickListener {
                navigate(R.id.action_alarmFragment_to_customAlarmFragment)
            }

            icBack.setOnClickListener {
                findNavController().popBackStack()
            }

            requireActivity().findViewById<ImageView>(R.id.drawer).gone()
        }


        slideUp()
        slideDown()
        return binding.root
    }

    private fun navigate(action: Int) {
        findNavController().navigate(action)
    }

    private fun slideDown() {
        binding.apply {
            cityOfAdamIv.setOnClickListener {
                val slideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
                goldNotePlate.startAnimation(slideDown)
                goldNotePlate.visible()
                postDelayed(500) {
                    goldClose.apply {
                        alpha = 0f
                        visible()
                        animate()
                            .alpha(1f)
                            .setDuration(shortAnimationDuration.toLong())
                            .setListener(null)

                    }
                }

            }
        }
    }

    private fun slideUp() {
        binding.apply {
            goldClose.setOnClickListener {
                val slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
                goldNotePlate.startAnimation(slideUp)
                goldNotePlate.gone()
                goldClose.gone()
            }
        }
    }
}