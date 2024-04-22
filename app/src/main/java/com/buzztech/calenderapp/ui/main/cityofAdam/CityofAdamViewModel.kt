package com.buzztech.calenderapp.ui.main.cityofAdam

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzztech.calenderapp.model.CityOfAdam
import com.buzztech.calenderapp.model.Knob
import com.buzztech.calenderapp.model.LotEvents
import com.buzztech.calenderapp.model.TreeOfLife
import com.buzztech.calenderapp.ui.main.treeoflife.TreeOfLiveViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CityofAdamViewModel : ViewModel() {

    private val _cityofAdamEvents = Channel<CityofAdamEvent>()
    val cityofAdamEventChannels = _cityofAdamEvents.receiveAsFlow()

    private val firebaseRef = FirebaseFirestore.getInstance()

    init {
        getCityOfAdamList()
        getLotEventList()
        getTreeData()
        getKnobData()
    }

    private fun getCityOfAdamList() {
        firebaseRef.collection("CityOfAdam").addSnapshotListener { snapShot, error ->
            if (error != null) {
                viewModelScope.launch {
                    _cityofAdamEvents.send(CityofAdamEvent.Error(error.message.toString()))
                }
                Log.d("LOGGER", "Exception " + error.message);
                return@addSnapshotListener;
            }
            val tempList = mutableListOf<CityOfAdam>()
            if (snapShot != null && !snapShot.isEmpty) {
                snapShot.forEach {
                    val model = it.toObject(CityOfAdam::class.java)
                    tempList.add(model)
                }
            }

            viewModelScope.launch {
                _cityofAdamEvents.send(CityofAdamEvent.CityOfAdamList(tempList))
            }
        }
    }

    private fun getTreeData(){
        firebaseRef.collection("Tree").addSnapshotListener { value, error ->
            if (error != null) {
                viewModelScope.launch {
                    _cityofAdamEvents.send(CityofAdamEvent.Error(error.message.toString()))
                }
                Log.d("LOGGER", "Exception " + error.message);
                return@addSnapshotListener;
            }
            val tempList = mutableListOf<TreeOfLife>()
            if (value != null && !value.isEmpty) {
                value.forEach {
                    val model = it.toObject(TreeOfLife::class.java)
                    tempList.add(model)
                }
            }

            viewModelScope.launch {
                _cityofAdamEvents.send(CityofAdamEvent.Success(tempList))
            }
        }
    }

    private fun getKnobData(){
        firebaseRef.collection("Knop").addSnapshotListener { value, error ->
            if (error != null) {
                viewModelScope.launch {
                    _cityofAdamEvents.send(CityofAdamEvent.Error(error.message.toString()))
                }
                Log.d("LOGGER", "Exception " + error.message);
                return@addSnapshotListener;
            }

            val tempList = mutableListOf<Knob>()
            if (value != null && !value.isEmpty) {
                value.forEach {
                    val model = it.toObject(Knob::class.java)
                    tempList.add(model)
                }
            }

            viewModelScope.launch {
                _cityofAdamEvents.send(CityofAdamEvent.KnobSuccess(tempList))
            }
        }
    }

    private fun getLotEventList() {
        firebaseRef.collection("Events").addSnapshotListener { snapShot, error ->
            if (error != null) {
                viewModelScope.launch {
                    _cityofAdamEvents.send(CityofAdamEvent.Error(error.message.toString()))
                }
                Log.d("LOGGER", "Exception " + error.message);
                return@addSnapshotListener;
            }
            val tempList = mutableListOf<LotEvents>()
            if (snapShot != null && !snapShot.isEmpty) {
                snapShot.forEach {
                    val model = it.toObject(LotEvents::class.java)
                    tempList.add(model)
                }
            }

            viewModelScope.launch {
                _cityofAdamEvents.send(CityofAdamEvent.LotEventsList(tempList))
            }
        }
    }

    sealed class CityofAdamEvent {
        data class Success(val treeData: MutableList<TreeOfLife>) : CityofAdamEvent()
        data class KnobSuccess(val knobData: MutableList<Knob>) :CityofAdamEvent()
        data class CityOfAdamList(val cityList: MutableList<CityOfAdam>) : CityofAdamEvent()
        data class LotEventsList(val lotEventList: MutableList<LotEvents>) : CityofAdamEvent()
        data class Error(val error: String) : CityofAdamEvent()
    }
}