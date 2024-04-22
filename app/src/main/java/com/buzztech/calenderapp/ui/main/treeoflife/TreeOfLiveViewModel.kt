package com.buzztech.calenderapp.ui.main.treeoflife

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzztech.calenderapp.model.Knob
import com.buzztech.calenderapp.model.TreeOfLife
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TreeOfLiveViewModel : ViewModel() {

    private val _treeOfLifeEvents = Channel<TreeOfLiveEvent>()
    val treeOfLifeChannel = _treeOfLifeEvents.receiveAsFlow()
    private var dialogData: String? = ""
    private val firebaseRef = FirebaseFirestore.getInstance()

    init {
        getTreeData()
        getKnobData()
        getTreeOfLife()
    }

    private fun getTreeData(){
        firebaseRef.collection("Tree").addSnapshotListener { value, error ->
            if (error != null) {
                viewModelScope.launch {
                    _treeOfLifeEvents.send(TreeOfLiveEvent.Error(error.message.toString()))
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
                _treeOfLifeEvents.send(TreeOfLiveEvent.Success(tempList))
            }
        }
    }

    private fun getKnobData(){
        firebaseRef.collection("Knop").addSnapshotListener { value, error ->
            if (error != null) {
                viewModelScope.launch {
                    _treeOfLifeEvents.send(TreeOfLiveEvent.Error(error.message.toString()))
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
                _treeOfLifeEvents.send(TreeOfLiveEvent.KnobSuccess(tempList))
            }
        }
    }

    private fun getTreeOfLife() {
        firebaseRef.collection("TreeOfLife").document("iQnl6PT3NndQY7AM9jLp").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    dialogData = it.getString("data")

                    viewModelScope.launch {
                        _treeOfLifeEvents.send(TreeOfLiveEvent.TreeOfLifeInfo(dialogData!!))
                    }
                }
            }.addOnFailureListener { error ->
                viewModelScope.launch {
                    _treeOfLifeEvents.send(TreeOfLiveEvent.Error(error.message.toString()))
                }
            }
    }

    sealed class TreeOfLiveEvent {
        data class Success(val treeData: MutableList<TreeOfLife>) : TreeOfLiveEvent()
        data class KnobSuccess(val knobData: MutableList<Knob>) : TreeOfLiveEvent()
        data class Error(val error: String) : TreeOfLiveEvent()
        data class TreeOfLifeInfo(val data: String) : TreeOfLiveEvent()
    }
}