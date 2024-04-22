package com.buzztech.calenderapp.ui.main.enochParts.solarParts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentSolarPartsBinding
import com.buzztech.calenderapp.ui.component.SimpleTextDialogFragment
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible
import com.google.firebase.firestore.FirebaseFirestore

class SolarPartsFragment : Fragment() {

    companion object {
        fun newInstance() = SolarPartsFragment()
    }

    private val firebaseRef = FirebaseFirestore.getInstance()

    private var _binding: FragmentSolarPartsBinding? = null
    private val binding get() = _binding!!
    private var dialogData :String? = ""
    private lateinit var textDialogFragment: SimpleTextDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSolarPartsBinding.inflate(layoutInflater)
        getSolarPartsDetails()
        binding.apply {
            cityOfAdamIv.setOnClickListener {
                textDialogFragment = SimpleTextDialogFragment(data = dialogData!!)
                textDialogFragment.show(childFragmentManager,"Text Dialog")
            }

            icBack?.setOnClickListener {
                findNavController().popBackStack()
            }

            requireActivity().findViewById<ImageView>(R.id.drawer).gone()
        }

        return binding.root
    }

    private fun getSolarPartsDetails(){
        binding.loader!!.root.visible()
        firebaseRef.collection("SolarParts18").document("S2LfC9Jgu4wp5SrguIqj").get().addOnSuccessListener {
            if (it.exists()){
                binding.loader!!.root.gone()
                dialogData = it.getString("data")
            }
        }.addOnFailureListener { error->
            binding.loader!!.root.gone()
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }
    }
}