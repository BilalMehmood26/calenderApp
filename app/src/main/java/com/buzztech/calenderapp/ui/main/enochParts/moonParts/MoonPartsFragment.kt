package com.buzztech.calenderapp.ui.main.enochParts.moonParts

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentMoonPartsBinding
import com.buzztech.calenderapp.model.FullMoon
import com.buzztech.calenderapp.ui.component.SimpleTextDialogFragment
import com.buzztech.calenderapp.ui.component.curveRecycler.ArcLayout
import com.buzztech.calenderapp.ui.component.curveRecycler.RvState
import com.buzztech.calenderapp.ui.component.curveRecycler.SnapOnScrollListener
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible
import com.google.firebase.firestore.FirebaseFirestore

class MoonPartsFragment : Fragment() {

    companion object {
        fun newInstance() = MoonPartsFragment()
    }

    private var moonArray: ArrayList<FullMoon> = ArrayList()

    private var _binding: FragmentMoonPartsBinding? = null
    private val binding get() = _binding!!

    private val firebaseRef = FirebaseFirestore.getInstance()

    private var dialogData :String? = ""
    private lateinit var textDialogFragment: SimpleTextDialogFragment

    private lateinit var snapHelper: LinearSnapHelper
    private lateinit var layoutManager: ArcLayout
    private var rvState = MutableLiveData(RvState())

    private lateinit var moonAdapter: MoonAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoonPartsBinding.inflate(layoutInflater)


        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_29,
                moonPhase = "waning part",
                moonDay = "14"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_28,
                moonPhase = "waning part",
                moonDay = "13"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_27,
                moonPhase = "waning part",
                moonDay = "12"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_26,
                moonPhase = "waning part",
                moonDay = "11"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_25,
                moonPhase = "waning part",
                moonDay = "10"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_24,
                moonPhase = "waning part",
                moonDay = "9"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_23,
                moonPhase = "waning part",
                moonDay = "8"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_22,
                moonPhase = "waning part",
                moonDay = "7"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_21,
                moonPhase = "waning part",
                moonDay = "6"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_20,
                moonPhase = "waning part",
                moonDay = "5"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_19,
                moonPhase = "waning part",
                moonDay = "4"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_18,
                moonPhase = "waning part",
                moonDay = "3"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_17,
                moonPhase = "waning part",
                moonDay = "2"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.moon_16,
                moonPhase = "waning part",
                moonDay = "1"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_0,
                moonPhase = "Black Moon",
                moonDay = "0"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_1,
                moonPhase = "waxing part",
                moonDay = "1"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_2,
                moonPhase = "waxing part",
                moonDay = "2"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_3,
                moonPhase = "waxing part",
                moonDay = "3"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_4,
                moonPhase = "waxing part",
                moonDay = "4"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_5,
                moonPhase = "waxing part",
                moonDay = "5"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_6,
                moonPhase = "waxing part",
                moonDay = "6"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_7,
                moonPhase = "waxing part",
                moonDay = "7"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_8,
                moonPhase = "waxing part",
                moonDay = "8"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_9,
                moonPhase = "waxing part",
                moonDay = "9"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_10,
                moonPhase = "waxing part",
                moonDay = "10"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_11,
                moonPhase = "waxing part",
                moonDay = "11"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_12,
                moonPhase = "waxing part",
                moonDay = "12"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_13,
                moonPhase = "waxing part",
                moonDay = "13"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.black_moon_14,
                moonPhase = "waxing part",
                moonDay = "14"
            )
        )
        moonArray.add(
            FullMoon(
                moonImg = R.drawable.full_moon,
                moonPhase = "full moon",
                moonDay = "15"
            )
        )


        binding.apply {
            initRv(moonRv)
            getMoonPartsDetails()
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

    private fun initRv(rv: RecyclerView) {

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val viewWidth = screenWidth / 6
        val viewHeight = (1.25f * viewWidth).toInt()

        rv.adapter = MoonAdapter(moonArray, rvState, viewWidth, viewHeight, requireContext()){
            if(it.contains("waning")){
                binding.imageView13.setImageResource(R.drawable.ic_waning)
            }else if(it.contains("full moon")){
                binding.imageView13.setImageResource(R.drawable.ic_full_moon)
            }else if(it.contains("Black Moon")){
                binding.imageView13.setImageResource(R.drawable.ic_black_moon)
            }else if(it.contains("waxing")){
                binding.imageView13.setImageResource(R.drawable.ic_waxing)
            }
        }
        rv.layoutManager = ArcLayout(resources, screenWidth, viewWidth, viewHeight).apply {
            layoutManager = this
        }

        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv)

        val snapOnScrollListener = SnapOnScrollListener(snapHelper)
        rv.addOnScrollListener(snapOnScrollListener)
        snapOnScrollListener.snapPosition.observe(viewLifecycleOwner) {
            rvState.apply { value = value?.copy(snapPosition = it)
            }
        }
    }

    private fun getMoonPartsDetails(){
        binding.loader.root.visible()
        firebaseRef.collection("SolarParts18").document("S2LfC9Jgu4wp5SrguIqj").get().addOnSuccessListener {
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