package com.buzztech.calenderapp.ui.main.lotdavid

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentLotofDavidBinding
import com.buzztech.calenderapp.ui.component.SimpleTextDialogFragment
import com.buzztech.calenderapp.ui.main.calendar.ViewPagerAdapter
import com.buzztech.calenderapp.ui.main.lotdavid.dayWatch.DayWatchFragment
import com.buzztech.calenderapp.ui.main.lotdavid.nightWatch.NightWatchFragment
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible
import com.google.firebase.firestore.FirebaseFirestore

class LotofDavidFragment : Fragment() {

    companion object {
        fun newInstance() = LotofDavidFragment()
    }

    private var _binding: FragmentLotofDavidBinding? = null
    private val binding get() = _binding!!
    private var fragmentList: ArrayList<Fragment> = ArrayList()

    private var dialogData :String? = ""
    private lateinit var textDialogFragment: SimpleTextDialogFragment
    private val firebaseRef = FirebaseFirestore.getInstance()

    private lateinit var viewModel: LotofDavidViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLotofDavidBinding.inflate(layoutInflater)
        binding.apply {
            icBack.setOnClickListener {
                findNavController().popBackStack()
            }

            requireActivity().findViewById<ImageView>(R.id.drawer).gone()
            getLotDetails()
            lotDialogIv.setOnClickListener {
                textDialogFragment = SimpleTextDialogFragment(data = dialogData!!)
                textDialogFragment.show(childFragmentManager,"Text Dialog")
            }

            fragmentList = arrayListOf(
                NightWatchFragment(),
                DayWatchFragment()
            )
            val viewPagerAdapter =
                ViewPagerAdapter(
                    fragmentList,
                    requireActivity().supportFragmentManager,
                    lifecycle
                )
            viewPager.adapter = viewPagerAdapter
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    if(position==0){
                        rootLayout.setBackgroundResource(R.drawable.bg_night)
                    }else{
                        rootLayout.setBackgroundResource(R.drawable.bg_morning)
                    }
                    super.onPageSelected(position)
                }
            })
            return binding.root
        }
    }

    private fun getLotDetails(){
        binding.loader.root.visible()
        firebaseRef.collection("Lots24Info").document("m5jzxvnxGOxrJh6HseWC").get().addOnSuccessListener {
            if (it.exists()){
                binding.loader.root.gone()
                dialogData = it.getString("data")
            }
        }.addOnFailureListener { error->
            binding.loader.root.gone()
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }
    }
}