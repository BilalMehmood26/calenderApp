package com.buzztech.calenderapp.ui.main.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzztech.calenderapp.model.HighlightsOfMonths
import com.buzztech.calenderapp.model.LotEvents
import com.buzztech.calenderapp.model.Months
import com.buzztech.calenderapp.ui.main.cityofAdam.CityofAdamViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CalenderViewModel : ViewModel() {
    private val _calenderEvents = Channel<CalenderEvent>()
    val calenderChannels = _calenderEvents.receiveAsFlow()

    private val firebaseRef = FirebaseFirestore.getInstance()


    init {
        getMonths()
    }

    private fun getMonths() {
        firebaseRef.collection("Month").addSnapshotListener { snapShot, error ->
            if (error != null) {
                viewModelScope.launch {
                    _calenderEvents.send(CalenderEvent.Error(error.message.toString()))
                }
                Log.d("LOGGER", "Exception " + error.message);
                return@addSnapshotListener;
            }

            val monthList = mutableListOf<Months>()
            if (snapShot != null && !snapShot.isEmpty) {
                snapShot.forEach {
                    val month = it.toObject(Months::class.java)
                    monthList.add(month)

                }
            }
            viewModelScope.launch {
                _calenderEvents.send(CalenderEvent.SummerMonthList(monthList))
            }

        }
    }

    sealed class CalenderEvent {
        data class SummerMonthList(val summerList: MutableList<Months>) : CalenderEvent()
        data class Error(val error: String) : CalenderEvent()
    }
}