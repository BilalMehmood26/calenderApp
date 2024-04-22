package com.buzztech.calenderapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.ActivityMainBinding
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.visible

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setDrawer()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        binding.apply {
            drawer.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }

        }

        when (navController.currentDestination!!.id) {
            R.id.cityofAdamFragment -> {
                binding.drawer.visible()
            }

            R.id.treeOfLiveFragment -> {
                binding.drawer.visible()
            }

            else -> {
                binding.drawer.gone()
            }
        }
        setContentView(binding.root)
    }

    private fun setDrawer() {
        binding.apply {
            cityOfAdamTv.setOnClickListener {
                navigate(R.id.cityofAdamFragment, "")
            }
            treeOfLifeTv.setOnClickListener {
                navigate(R.id.treeOfLiveFragment, "")
            }
            calendarTv.setOnClickListener {
                navigate(R.id.calenderFragment, "")
            }

            knowledgeTv.setOnClickListener {
                if (knowledgeGroup.visibility == View.VISIBLE) {
                    knowledgeGroup.visibility = View.GONE
                } else {
                    knowledgeGroup.visibility = View.VISIBLE
                }
            }

            moonTv.setOnClickListener {
                navigate(R.id.knowledgeFragment, "Moon")
            }

            highHolyDaysTv.setOnClickListener {
                navigate(R.id.knowledgeFragment, "High Holy Days")
            }

            sunTv.setOnClickListener {
                navigate(R.id.knowledgeFragment, "Sun")
            }

            windTv.setOnClickListener {
                navigate(R.id.knowledgeFragment, "Wind")
            }

            treeOfLifeKnowledgeTv.setOnClickListener {
                navigate(R.id.knowledgeFragment, "Tree Of Life")
            }

            reckoningOfTheYearTv.setOnClickListener {
                navigate(R.id.knowledgeFragment, "Reckoning Of The Year")
            }

            constellationsTv.setOnClickListener {
                navigate(R.id.knowledgeFragment, "Constellations")
            }

            courseOfDavidTv.setOnClickListener {
                navigate(R.id.knowledgeFragment, "City Of Adam")
            }


            cycleOfYearTv.setOnClickListener {
                navigate(R.id.cycleOfYearFragment, "")
            }
            lotsOfDavidTv.setOnClickListener {
                navigate(R.id.lotofDavidFragment, "")
            }

            enochPartsTv.setOnClickListener {
                if (enochGroup.visibility == View.VISIBLE) {
                    enochGroup.visibility = View.GONE
                } else {
                    enochGroup.visibility = View.VISIBLE
                }
            }

            solarPartsTv.setOnClickListener {
                navigate(R.id.solarPartsFragment, "")
            }
            moonPartsTv.setOnClickListener {
                navigate(R.id.moonPartsFragment, "")
            }
            alarmTv.setOnClickListener {
                navigate(R.id.alarmFragment, "")
            }
            settingsTv.setOnClickListener {
                navigate(R.id.settingFragment, "")
            }

            logoutTv.setOnClickListener {
                finish()
            }
        }
    }

    private fun navigate(action: Int, argument: String) {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        binding.enochGroup.visibility = View.GONE
        binding.knowledgeGroup.visibility = View.GONE
        val bundle = Bundle()
        bundle.putString("knowledge", argument)
        findNavController(R.id.fragmentContainer).navigate(action, bundle)
    }

}