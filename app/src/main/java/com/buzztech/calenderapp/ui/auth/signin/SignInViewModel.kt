package com.buzztech.calenderapp.ui.auth.signin

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    //Events Channel
    private val signInEventChannel = Channel<SignInEvent>()
    val signInEvent = signInEventChannel.receiveAsFlow()

    private val mAuth: FirebaseAuth = Firebase.auth

    fun continueLogin(email: String, password: String) {

        when {
            email.isEmpty() -> {
                viewModelScope.launch { signInEventChannel.send(SignInEvent.Error("Please Enter Email!")) }
            }

            !isEmailValid(email) -> {
                viewModelScope.launch { signInEventChannel.send(SignInEvent.Error("Please Enter Valid Email!")) }
            }

            password.isEmpty() -> {
                viewModelScope.launch { signInEventChannel.send(SignInEvent.Error("Please enter password!")) }
            }

            else -> {
                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModelScope.launch {
                    signInEventChannel.send(SignInEvent.Success)
                }
            } else {
                viewModelScope.launch { signInEventChannel.send(SignInEvent.Error(task.exception!!.message.toString())) }
            }
        }
    }

    fun navigateToSignup() {
        viewModelScope.launch { signInEventChannel.send(SignInEvent.NavigateToSignUp) }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    sealed class SignInEvent {
        data object NavigateToSignUp : SignInEvent()
        data object Success : SignInEvent()
        data class Error(val error: String) : SignInEvent()
    }
}