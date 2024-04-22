package com.buzztech.calenderapp.ui.component

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.buzztech.calenderapp.databinding.FragmentSetNotificationDialogBinding
import com.buzztech.calenderapp.model.Notification
import com.buzztech.calenderapp.ui.main.calendar.CalenderAdapter
import com.buzztech.calenderapp.ui.main.calendar.setNotification.NotificationAdapter
import com.buzztech.calenderapp.utils.postDelayed

class SetNotificationDialog : DialogFragment() {

    private lateinit var notificationAdapter: NotificationAdapter
    private var notificationList : ArrayList<Notification> = ArrayList()

    private var _binding: FragmentSetNotificationDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetNotificationDialogBinding.inflate(layoutInflater)

        notificationList = arrayListOf(
            Notification("13th Lot of HUPPAH – 7PM UTC – 9/09/23 "),
            Notification("14TH Lot of YESHABEAB – 8PM UTC – 9/09/23 "),
            Notification("15TH Lot of BILGAH – 9PM UTC – 9/09/23 "),
            Notification("16TH Lot of IMMER – 10PM UTC – 9/09/23 "),
            Notification("17TH Lot of HEZIR – 11PM UTC – 9/09/23 "),
            Notification("18TH Lot APHSES – 12AM UTC – 9/10/23 "),
            Notification("19TH Lot of PETHAHIAH – 1AM UTC – 9/10/23 "),
            Notification("20TH Lot of YEHEZEKEL – 2AM UTC – 9/10/23 "),
            Notification("21ST Lot of YACHIN – 3AM UTC – 9/10/23 "),
            Notification("22ND Lot of GAMUL – 4AM UTC – 9/10/23 "),
            Notification("23RD Lot of DELAIAH – 5AM UTC – 9/10/23 "),
            Notification("24TH Lot of MAAZIAH – 6AM UTC – 9/10/23 "),
            Notification("1ST Lot of YEHOIARIB – 7AM UTC – 9/10/23 "),
            Notification("2ND Lot of YEDAIAH – 8AM UTC – 9/10/23 "),
            Notification("3RD Lot of HARIM – 9AM UTC – 9/10/23 "),
            Notification("4TH Lot of SEORIM – 10AM UTC – 9/10/23 "),
            Notification("5TH Lot of MALCHIYAH – 11AM UTC – 9/10/23 "),
            Notification("6TH Lot of MIYAMIN – 12PM UTC – 9/10/23 "),
            Notification("7TH Lot of HAKKOZ – 1PM UTC – 9/10/23 "),
            Notification("8TH Lot of ABIYAH – 2PM UTC – 9/10/23 "),
            Notification("9TH Lot of YESHUA – 3PM UTC – 9/10/23 "),
            Notification("10TH Lot of SHECANIAH – 4PM UTC – 9/10/23 "),
            Notification("11TH Lot of ELIASHIB – 5PM UTC – 9/10/23 "),
            Notification("12TH Lot of YAKIM – 6PM UTC – 9/10/23 ")
        )

        binding.apply {
            postDelayed(300) {
                group.visibility = View.VISIBLE
                goldNotePlate.visibility = View.VISIBLE
            }


            notificationAdapter = NotificationAdapter(notificationList)
            setNotiRv.apply {
                adapter = notificationAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            goldClose.setOnClickListener {
                dismiss()
            }
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