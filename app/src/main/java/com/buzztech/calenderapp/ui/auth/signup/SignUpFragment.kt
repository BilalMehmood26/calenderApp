package com.buzztech.calenderapp.ui.auth.signup

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentSignUpBinding
import com.buzztech.calenderapp.ui.auth.signin.SignInViewModel
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding.apply {
            signinBtn.setOnClickListener {
                findNavController().popBackStack()
            }

            signupBtn.setOnClickListener {
                progressBar.visible()
                viewModel.continueSignUp(
                    firstNameEt.text.toString(),
                    lastNameEt.text.toString(),
                    emailLy.text.toString(),
                    phoneNumberEt.text.toString(),
                    passwordLy.text.toString(),
                )
            }
        }
        observer()
        return binding.root
    }

    private fun observer() {

        lifecycleScope.launch {
            viewModel.signUpChannelEvents.collect { event ->
                when (event) {
                    is SignUpViewModel.SignUpEvent.Error -> {
                        binding.progressBar.gone()
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }

                    SignUpViewModel.SignUpEvent.Success -> {
                        binding.progressBar.gone()
                        findNavController().navigate(R.id.action_signUpFragment_to_memberShipFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}