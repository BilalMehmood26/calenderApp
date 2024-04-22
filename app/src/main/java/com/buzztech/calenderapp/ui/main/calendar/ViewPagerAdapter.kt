package com.buzztech.calenderapp.ui.main.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val fragmentList: ArrayList<Fragment>,
    fm: FragmentManager,
    lifeCycle: Lifecycle
) : FragmentStateAdapter(fm, lifeCycle) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}