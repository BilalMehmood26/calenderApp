package com.buzztech.calenderapp.ui.main.alarm.customalarm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentCustomAlarmBinding
import com.buzztech.calenderapp.databinding.FragmentMemorialDaysBinding
import com.buzztech.calenderapp.ui.component.SetNotificationDialog
import com.buzztech.calenderapp.ui.component.SimpleTextDialogFragment
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible

class CustomAlarmFragment : Fragment() {


    private var _binding: FragmentCustomAlarmBinding? = null
    private val binding get() = _binding!!
    private lateinit var setNotificationDialog: SetNotificationDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomAlarmBinding.inflate(layoutInflater)
        binding.apply {
            alarmBtn.setOnClickListener {
                setNotificationDialog = SetNotificationDialog()
                setNotificationDialog.show(childFragmentManager, "NotificationDialog")
            }

            icBack.setOnClickListener {
                findNavController().popBackStack()
            }

            requireActivity().findViewById<ImageView>(R.id.drawer).gone()
        }
        return binding.root
    }
}