package com.buzztech.calenderapp.ui.main.calendar.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzztech.calenderapp.model.Calender
import com.buzztech.calenderapp.model.HighlightsOfMonths
import com.buzztech.calenderapp.ui.main.calendar.CalenderViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CalenderDetailsViewModel : ViewModel() {

    private val _calenderDetailsEvents = Channel<CalenderDetailsEvent>()
    val calenderDetailsChannels = _calenderDetailsEvents.receiveAsFlow()

    private val firebaseRef = FirebaseFirestore.getInstance()

    init {
        getMonth()
        getMonthsHighLight()
    }

    private fun getMonth(){
        firebaseRef.collection("Calendar").addSnapshotListener { snapShot, error ->
            if (error != null) {
                viewModelScope.launch {
                    _calenderDetailsEvents.send(CalenderDetailsEvent.Error(error.message.toString()))
                }
                Log.d("LOGGER", "Exception " + error.message);
                return@addSnapshotListener;
            }

            val daysList = mutableListOf<Calender>()
            if (snapShot != null && !snapShot.isEmpty) {
                snapShot.forEach {
                    val month = it.toObject(Calender::class.java)
                    daysList.add(month)

                }
            }
            viewModelScope.launch {
                _calenderDetailsEvents.send(CalenderDetailsEvent.daysList(daysList))
            }
        }
    }

    private fun getMonthsHighLight(){
        firebaseRef.collection("HighlightsOfMonth").addSnapshotListener { value, error ->
            if (error != null) {
                viewModelScope.launch {
                    _calenderDetailsEvents.send(CalenderDetailsEvent.Error(error.message.toString()))
                }
                return@addSnapshotListener;
            }

            val monthHighlightsList = mutableListOf<HighlightsOfMonths>()
            if (value != null && !value.isEmpty) {
                value.forEach {
                    val month = it.toObject(HighlightsOfMonths::class.java)
                    monthHighlightsList.add(month)

                }
            }
            viewModelScope.launch {
                _calenderDetailsEvents.send(CalenderDetailsEvent.MonthHighLightsList(monthHighlightsList))
            }
        }
    }
    sealed class CalenderDetailsEvent {
        data class daysList(val daysList: MutableList<Calender>) : CalenderDetailsEvent()
        data class MonthHighLightsList(val highlightsList: MutableList<HighlightsOfMonths>) : CalenderDetailsEvent()
        data class Error(val error: String) : CalenderDetailsEvent()
    }
}