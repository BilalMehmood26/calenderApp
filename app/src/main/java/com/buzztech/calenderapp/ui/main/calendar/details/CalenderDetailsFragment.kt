package com.buzztech.calenderapp.ui.main.calendar.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentCalenderDetailsBinding
import com.buzztech.calenderapp.model.HighlightsOfMonths
import com.buzztech.calenderapp.model.MonthName
import com.buzztech.calenderapp.ui.component.CalenderEventDialogFragment
import com.buzztech.calenderapp.ui.component.CityOfAdamDialogFragment
import com.buzztech.calenderapp.ui.component.TextDialogFragment
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CalenderDetailsFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = CalenderDetailsFragment()
    }

    private lateinit var calenderMainEventDialog: CalenderEventDialogFragment
    private lateinit var highLightofMonthEventDialog: TextDialogFragment

    private var _binding: FragmentCalenderDetailsBinding? = null
    private val binding get() = _binding!!

    private var daysList: ArrayList<String> = ArrayList()
    private var monthNum: Long = 0
    private var monthImage: String = ""
    private lateinit var highlightsOfMonths: HighlightsOfMonths

    private lateinit var viewModel: CalenderDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderDetailsBinding.inflate(layoutInflater)

        monthNum = requireArguments().getLong("month")

        viewModel = ViewModelProvider(this).get(CalenderDetailsViewModel::class.java)

        binding.apply {

            btn1.setOnClickListener(this@CalenderDetailsFragment)
            btn2.setOnClickListener(this@CalenderDetailsFragment)
            btn3.setOnClickListener(this@CalenderDetailsFragment)
            btn4.setOnClickListener(this@CalenderDetailsFragment)
            btn5.setOnClickListener(this@CalenderDetailsFragment)
            btn6.setOnClickListener(this@CalenderDetailsFragment)
            btn7.setOnClickListener(this@CalenderDetailsFragment)
            btn8.setOnClickListener(this@CalenderDetailsFragment)
            btn9.setOnClickListener(this@CalenderDetailsFragment)
            btn10.setOnClickListener(this@CalenderDetailsFragment)
            btn11.setOnClickListener(this@CalenderDetailsFragment)
            btn12.setOnClickListener(this@CalenderDetailsFragment)
            btn13.setOnClickListener(this@CalenderDetailsFragment)
            btn14.setOnClickListener(this@CalenderDetailsFragment)
            btn15.setOnClickListener(this@CalenderDetailsFragment)
            btn16.setOnClickListener(this@CalenderDetailsFragment)
            btn17.setOnClickListener(this@CalenderDetailsFragment)
            btn18.setOnClickListener(this@CalenderDetailsFragment)
            btn19.setOnClickListener(this@CalenderDetailsFragment)
            btn20.setOnClickListener(this@CalenderDetailsFragment)
            btn21.setOnClickListener(this@CalenderDetailsFragment)
            btn22.setOnClickListener(this@CalenderDetailsFragment)
            btn23.setOnClickListener(this@CalenderDetailsFragment)
            btn24.setOnClickListener(this@CalenderDetailsFragment)
            btn25.setOnClickListener(this@CalenderDetailsFragment)
            btn26.setOnClickListener(this@CalenderDetailsFragment)
            btn27.setOnClickListener(this@CalenderDetailsFragment)
            btn28.setOnClickListener(this@CalenderDetailsFragment)
            btn29.setOnClickListener(this@CalenderDetailsFragment)
            btn30.setOnClickListener(this@CalenderDetailsFragment)
            btn31.setOnClickListener(this@CalenderDetailsFragment)
            btn32.setOnClickListener(this@CalenderDetailsFragment)
            btn33.setOnClickListener(this@CalenderDetailsFragment)
            btn34.setOnClickListener(this@CalenderDetailsFragment)
            btn35.setOnClickListener(this@CalenderDetailsFragment)

            textView.text = "$monthNum Month"
            cityOfAdamIv.setOnClickListener {
                calenderMainEventDialog = CalenderEventDialogFragment()
                calenderMainEventDialog.show(childFragmentManager, "calender")
            }

            highLightsIv.setOnClickListener {
                highLightofMonthEventDialog = TextDialogFragment(highlightsOfMonths)
                highLightofMonthEventDialog.show(childFragmentManager, "HighLights")
            }

            icBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        observer()
        return binding.root
    }

    private fun observer() {

        lifecycleScope.launch {
            viewModel.calenderDetailsChannels.collect { event ->
                when (event) {
                    is CalenderDetailsViewModel.CalenderDetailsEvent.Error -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }

                    is CalenderDetailsViewModel.CalenderDetailsEvent.daysList -> {

                        event.daysList.forEach {
                            if (it.monthNo.equals(monthNum)) {
                                binding.apply {
                                    val circularProgressDrawable =
                                        CircularProgressDrawable(requireContext())
                                    circularProgressDrawable.strokeWidth = 5f
                                    circularProgressDrawable.centerRadius = 30f
                                    circularProgressDrawable.start()
                                    monthImage = it.monthImage
                                    monthIv.load(monthImage) {
                                        crossfade(enable = true)
                                        placeholder(circularProgressDrawable)
                                    }
                                }
                                daysList.addAll(it.days)
                            }
                        }
                    }

                    is CalenderDetailsViewModel.CalenderDetailsEvent.MonthHighLightsList -> {
                        event.highlightsList.forEach {
                            val monthEnum = MonthName.valueOf(it.monthName.uppercase())
                            if (monthEnum.monthName == it.monthName) {
                                if (monthEnum.monthNumber == monthNum) {
                                    Toast.makeText(requireContext(), it.monthName, Toast.LENGTH_SHORT).show()
                                    highlightsOfMonths = HighlightsOfMonths(
                                        data = it.data,
                                        monthName = it.monthName
                                    )
                                    Log.d("higholidays", "observer: ${it.data.get("Biblical History")}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_1 -> {
                if (!daysList[0].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[0]
                    )
                }
            }

            R.id.btn_2 -> {
                if (!daysList[1].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[1]
                    )
                }
            }

            R.id.btn_3 -> {
                if (!daysList[2].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[2]
                    )
                }
            }

            R.id.btn_4 -> {
                if (!daysList[3].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[3]
                    )
                }
            }

            R.id.btn_5 -> {
                if (!daysList[4].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[4]
                    )
                }
            }

            R.id.btn_6 -> {
                if (!daysList[5].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[5]
                    )
                }
            }

            R.id.btn_7 -> {
                if (!daysList[6].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[6]
                    )
                }
            }

            R.id.btn_8 -> {
                if (!daysList[7].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[7]
                    )
                }
            }

            R.id.btn_9 -> {
                if (!daysList[8].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[8]
                    )
                }
            }

            R.id.btn_10 -> {
                if (!daysList[9].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[9]
                    )
                }
            }

            R.id.btn_11 -> {
                if (!daysList[10].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[10]
                    )
                }
            }

            R.id.btn_12 -> {
                if (!daysList[11].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[11]
                    )
                }
            }

            R.id.btn_13 -> {
                if (!daysList[12].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[12]
                    )
                }
            }

            R.id.btn_14 -> {
                if (!daysList[13].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[13]
                    )
                }
            }

            R.id.btn_15 -> {
                if (!daysList[14].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[14]
                    )
                }
            }

            R.id.btn_16 -> {
                if (!daysList[15].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[15]
                    )
                }
            }

            R.id.btn_17 -> {
                if (!daysList[16].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[16]
                    )
                }
            }

            R.id.btn_17 -> {
                navigate(
                    R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                    daysList[16]
                )
            }

            R.id.btn_18 -> {
                if (!daysList[17].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[17]
                    )
                }
            }

            R.id.btn_19 -> {
                if (!daysList[18].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[18]
                    )
                }
            }

            R.id.btn_20 -> {
                if (!daysList[19].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[19]
                    )
                }
            }

            R.id.btn_21 -> {
                if (!daysList[20].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[20]
                    )
                }
            }

            R.id.btn_22 -> {
                if (!daysList[21].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[21]
                    )
                }
            }

            R.id.btn_23 -> {
                if (!daysList[22].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[22]
                    )
                }
            }

            R.id.btn_24 -> {
                if (!daysList[23].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[23]
                    )
                }
            }

            R.id.btn_25 -> {
                if (!daysList[24].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[24]
                    )
                }
            }

            R.id.btn_26 -> {
                if (!daysList[25].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[25]
                    )
                }
            }

            R.id.btn_27 -> {
                if (!daysList[26].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[26]
                    )
                }
            }

            R.id.btn_28 -> {
                if (!daysList[27].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[27]
                    )
                }
            }

            R.id.btn_29 -> {
                if (!daysList[28].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[28]
                    )
                }
            }

            R.id.btn_30 -> {
                if (!daysList[29].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[29]
                    )
                }
            }

            R.id.btn_31 -> {
                if (!daysList[30].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[30]
                    )
                }
            }

            R.id.btn_32 -> {
                if (!daysList[31].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[31]
                    )
                }
            }

            R.id.btn_33 -> {
                if (!daysList[32].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[32]
                    )
                }
            }

            R.id.btn_34 -> {
                if (!daysList[33].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[33]
                    )
                }
            }

            R.id.btn_35 -> {
                if (!daysList[34].equals("newMonth")) {
                    navigate(
                        R.id.action_calenderDetailsFragment_to_setNotificationFragment,
                        daysList[34]
                    )
                }
            }
        }
    }

    private fun navigate(action: Int, argument: String) {
        val bundle = Bundle()
        bundle.putString("image", argument)
        findNavController().navigate(action, bundle)
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            val circularProgressDrawable = CircularProgressDrawable(requireContext())
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            monthIv.load(monthImage) {
                crossfade(enable = true)
                placeholder(circularProgressDrawable)
            }
        }
    }
}