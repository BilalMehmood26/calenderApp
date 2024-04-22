package com.buzztech.calenderapp.ui.main.calendar.setNotification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentSetNotificationBinding
import com.buzztech.calenderapp.ui.component.SetNotificationDialog
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible

class SetNotificationFragment : Fragment() {

    private var _binding: FragmentSetNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var setNotificationDialog: SetNotificationDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetNotificationBinding.inflate(layoutInflater)

        binding.apply {
            val circularProgressDrawable = CircularProgressDrawable(requireContext())
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            dayIv.load(requireArguments().getString("image")){
                crossfade(true)
                placeholder(circularProgressDrawable)
                moonIv.visible()
            }

            setNotiIv.setOnClickListener {
                setNotificationDialog = SetNotificationDialog()
                setNotificationDialog.show(childFragmentManager,"NotificationDialog")
            }

            icBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

}