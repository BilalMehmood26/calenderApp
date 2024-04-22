package com.buzztech.calenderapp.ui.main.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzztech.calenderapp.model.CityOfAdam
import com.buzztech.calenderapp.model.LotEvents
import com.buzztech.calenderapp.model.UserModel
import com.buzztech.calenderapp.ui.main.cityofAdam.CityofAdamViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingViewModel : ViewModel() {
    private val _settingEvents = Channel<SettingEvent>()
    val settingChannels = _settingEvents.receiveAsFlow()

    private val firebaseRef = FirebaseFirestore.getInstance()

    init {
        getUserDetails()
    }

    private fun getUserDetails(){
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        firebaseRef.collection("Users").document(userId).get().addOnSuccessListener { task->
            val user = task.toObject(UserModel::class.java)
            viewModelScope.launch {
                _settingEvents.send(SettingEvent.Success(user!!))
            }
        }
    }

    sealed class SettingEvent {
        data class Success(val userDetails: UserModel) : SettingEvent()
        data class Error(val error: String) : SettingEvent()
    }
}