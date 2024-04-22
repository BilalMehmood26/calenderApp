package com.buzztech.calenderapp.ui.main.knowledge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzztech.calenderapp.model.Knowledge
import com.buzztech.calenderapp.model.LotEvents
import com.buzztech.calenderapp.ui.main.cityofAdam.CityofAdamViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class KnowledgeViewModel : ViewModel() {

    private val _knowledgeEvents = Channel<KnowledgeEvent>()
    val knowledgeEventChannel = _knowledgeEvents.receiveAsFlow()

    private val firebaseRef = FirebaseFirestore.getInstance()

    init {

        getKnowledgeData()
    }

    private fun getKnowledgeData() {
        firebaseRef.collection("Knowledge").addSnapshotListener { value, error ->
            if (error != null) {
                viewModelScope.launch {
                    _knowledgeEvents.send(KnowledgeEvent.Error(error.message.toString()))
                }
                Log.d("LOGGER", "Exception " + error.message);
                return@addSnapshotListener;
            }
            val tempList = mutableListOf<Knowledge>()
            if (value != null && !value.isEmpty) {
                value.forEach {
                    val model = it.toObject(Knowledge::class.java)
                    tempList.add(model)
                }
            }

            viewModelScope.launch {
                _knowledgeEvents.send(KnowledgeEvent.KnowledgeDataList(tempList))
            }

        }
    }

    sealed class KnowledgeEvent {
        data class KnowledgeDataList(val knowledgeList: MutableList<Knowledge>) : KnowledgeEvent()
        data class Error(val error: String) : KnowledgeEvent()
    }

}