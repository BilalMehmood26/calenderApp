package com.buzztech.calenderapp.ui.main.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentSettingBinding
import com.buzztech.calenderapp.ui.auth.AuthActivity
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.postDelayed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private var _binding :FragmentSettingBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)

        binding.apply {
            logOutBtn.setOnClickListener {
                postDelayed(500){
                    Firebase.auth.signOut()
                    startActivity(Intent(requireContext(),AuthActivity::class.java))
                    requireActivity().finish()
                }
            }
            icBack.setOnClickListener {
                findNavController().popBackStack()
            }

            requireActivity().findViewById<ImageView>(R.id.drawer).gone()

        }
        observer()
        return binding.root
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.settingChannels.collect{event->
                when(event){
                    is SettingViewModel.SettingEvent.Error -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }
                    is SettingViewModel.SettingEvent.Success -> {
                        binding.apply {
                            event.userDetails.apply {
                                firstNameEt.setText(firstName)
                                lastNameEt.setText(lastName)
                                emailEt.setText(email)
                                passwordEt.setText(password)
                                profilrName.setText("$firstName $lastName" )
                            }
                        }
                    }
                }
            }
        }
    }
}