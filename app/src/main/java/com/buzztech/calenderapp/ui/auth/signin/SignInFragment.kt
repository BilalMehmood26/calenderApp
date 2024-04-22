package com.buzztech.calenderapp.ui.auth.signin

import android.content.Intent
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
import com.buzztech.calenderapp.databinding.FragmentSignInBinding
import com.buzztech.calenderapp.ui.main.MainActivity
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding.apply {
            signupBtn.setOnClickListener {
                progressBar.visible()
                viewModel.navigateToSignup()
            }

            loginBtn.setOnClickListener {
                progressBar.visible()
                viewModel.continueLogin(
                    email = emailLy.text.toString(),
                    password = passwordLy.text.toString()
                )
            }
        }

        observer()
        return binding.root
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.signInEvent.collect { event ->
                when (event) {
                    is SignInViewModel.SignInEvent.Error -> {
                        binding.progressBar.gone()
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }
                    SignInViewModel.SignInEvent.NavigateToSignUp -> {
                        binding.progressBar.gone()
                        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
                    }
                    SignInViewModel.SignInEvent.Success -> {
                        binding.progressBar.gone()
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
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