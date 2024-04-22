package com.buzztech.calenderapp.ui.component.curveRecycler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.buzztech.calenderapp.utils.getSnapPosition

class SnapOnScrollListener(
    private val snapHelper: SnapHelper
) : RecyclerView.OnScrollListener() {
    private val rvSnapPos = MutableLiveData(RecyclerView.NO_POSITION)

    val snapPosition: LiveData<Int>
        get() = rvSnapPos

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            maybeNotifySnapPositionChange(recyclerView)
        }
    }

    private fun maybeNotifySnapPositionChange(recyclerView: RecyclerView) {
        val snapPosition = snapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = rvSnapPos.value != snapPosition
        if (snapPositionChanged) {
            rvSnapPos.value = snapPosition
        }
    }
}