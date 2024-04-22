package com.buzztech.calenderapp.ui.auth.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzztech.calenderapp.model.UserModel
import com.buzztech.calenderapp.utils.hasSpecialCharacters
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val _signUpEvents = Channel<SignUpEvent>()
    val signUpChannelEvents = _signUpEvents.receiveAsFlow()

    private val mAuth = Firebase.auth
    private val firebaseRef = FirebaseFirestore.getInstance()

    fun continueSignUp(
        fName: String,
        lName: String,
        email: String,
        phone: String,
        password: String
    ) {

        when {
            isValidName(fName) -> {
                viewModelScope.launch { _signUpEvents.send(SignUpEvent.Error("Please enter valid name")) }
            }

            isValidName(lName) -> {
                viewModelScope.launch { _signUpEvents.send(SignUpEvent.Error("Please enter valid name")) }
            }

            isEmailValid(email) -> {
                viewModelScope.launch { _signUpEvents.send(SignUpEvent.Error("Please enter valid email")) }
            }

            !isPasswordValid(password) -> {
                viewModelScope.launch { _signUpEvents.send(SignUpEvent.Error("Please enter valid password")) }
            }

            phone.isEmpty() -> {
                viewModelScope.launch { _signUpEvents.send(SignUpEvent.Error("Please enter contact number")) }
            }

            else -> {
                createUser(fName, lName, email, phone, password)
            }
        }
    }

    private fun createUser(
        fName: String,
        lName: String,
        email: String,
        phone: String,
        password: String
    ) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: FirebaseUser? = task.result.user
                createUserDetails(
                    user!!.uid,
                    fName,
                    lName,
                    email,
                    phone,
                    password
                )
            } else {
                viewModelScope.launch { _signUpEvents.send(SignUpEvent.Error(task.exception!!.message.toString())) }
            }
        }
    }

    private fun createUserDetails(
        userID: String,
        fName: String,
        lName: String,
        email: String,
        phone: String,
        password: String
    ) {
        val userModel = UserModel(
            id = userID,
            firstName = fName,
            lastName = lName,
            email = email,
            phone = phone,
            password = password
        )
        firebaseRef.collection("Users").document(userID).set(userModel)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch { _signUpEvents.send(SignUpEvent.Success) }
                } else {
                    viewModelScope.launch { _signUpEvents.send(SignUpEvent.Error(task.exception!!.message.toString())) }
                }
            }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    private fun isValidName(name: String): Boolean {

        return if (name.hasSpecialCharacters()) {
            false
        } else {
            name.isEmpty()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    sealed class SignUpEvent {
        data object Success : SignUpEvent()
        data class Error(val error: String) : SignUpEvent()
    }
}