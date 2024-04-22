package com.buzztech.calenderapp.ui.auth.membership

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentMemberShipBinding
import com.buzztech.calenderapp.databinding.FragmentSignUpBinding

class MemberShipFragment : Fragment() {

    companion object {
        fun newInstance() = MemberShipFragment()
    }

    private var _binding: FragmentMemberShipBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MemberShipViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemberShipBinding.inflate(layoutInflater)

        binding.apply {
            payNowBtn.setOnClickListener {
                findNavController().navigate(R.id.action_memberShipFragment_to_signInFragment)
            }

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}