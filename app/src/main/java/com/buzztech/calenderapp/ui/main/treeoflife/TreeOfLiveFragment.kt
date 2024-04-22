package com.buzztech.calenderapp.ui.main.treeoflife

import android.graphics.drawable.TransitionDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentTreeOfLiveBinding
import com.buzztech.calenderapp.model.Knob
import com.buzztech.calenderapp.model.LotEvents
import com.buzztech.calenderapp.model.TreeOfLife
import com.buzztech.calenderapp.ui.component.CityOfAdamDialogFragment
import com.buzztech.calenderapp.ui.component.SimpleTextDialogFragment
import com.buzztech.calenderapp.ui.component.TreeofLifeDialogFragment
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.goneViews
import com.buzztech.calenderapp.utils.visible
import com.buzztech.calenderapp.utils.visibleViews
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TreeOfLiveFragment : Fragment() {

    companion object {
        fun newInstance() = TreeOfLiveFragment()
    }

    private lateinit var viewModel: TreeOfLiveViewModel
    private lateinit var treeOfLifeDialog: TreeofLifeDialogFragment

    private var _binding: FragmentTreeOfLiveBinding? = null
    private val binding get() = _binding!!
    private var dialogData: String? = ""

    private lateinit var textDialogFragment: SimpleTextDialogFragment
    private lateinit var gestureDetector: GestureDetector

    private val firebaseRef = FirebaseFirestore.getInstance()
    private var treeOfLifeList: ArrayList<TreeOfLife> = ArrayList()
    private var knobList: ArrayList<Knob> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTreeOfLiveBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(TreeOfLiveViewModel::class.java)

        binding.apply {
            gestureDetector = GestureDetector(requireContext(), GestureListener())

            cityOfAdamIv.setOnClickListener {
                textDialogFragment = SimpleTextDialogFragment(data = dialogData!!)
                textDialogFragment.show(childFragmentManager, "Text Dialog")
            }

            root.setOnTouchListener { View, motionEvent ->
                gestureDetector.onTouchEvent(motionEvent)
                true
            }

            knobOne.setOnClickListener {
                val firstKnob = getKnobData(1)
                Toast.makeText(requireContext(), "${firstKnob.month}", Toast.LENGTH_SHORT).show()

                treeOfLifeDialog = TreeofLifeDialogFragment(
                    firstKnob.data,
                    month = "1st Month",
                    branch = "1st Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            knobTwo.setOnClickListener {
                val knobData = getKnobData(2)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "2nd Month",
                    branch = "2nd Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }
            knobThree.setOnClickListener {
                val knobData = getKnobData(3)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "3rd Month",
                    branch = "3rd Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }
            knobFour.setOnClickListener {
                val knobData = getKnobData(4)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "4th Month",
                    branch = "4th Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }
            knobFive.setOnClickListener {
                val knobData = getKnobData(5)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "5th Month",
                    branch = "5th Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }
            knobSix.setOnClickListener {
                val knobData = getKnobData(6)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "6th Month",
                    branch = "6th Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            knobSeven.setOnClickListener {
                val knobData = getKnobData(7)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "7th Month",
                    branch = "7th Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            knobEight.setOnClickListener {
                val knobData = getKnobData(8)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "8th Month",
                    branch = "8th Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            knobNine.setOnClickListener {
                val knobData = getKnobData(9)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "9th Month",
                    branch = "9th Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            knobTen.setOnClickListener {
                val knobData = getKnobData(10)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "10th Month",
                    branch = "10th Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            knobEleven.setOnClickListener {
                val knobData = getKnobData(11)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "11th Month",
                    branch = "11th Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            knobTwelve.setOnClickListener {
                val knobData = getKnobData(12)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "12th Month",
                    branch = "12th Branch"
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            centerKnobOne.setOnClickListener {
                val knobData = getCenterKnobData(1)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "1st knob",
                    branch = knobData.name
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            centerKnobTwo.setOnClickListener {
                val knobData = getCenterKnobData(2)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "2nd knob",
                    branch = knobData.name
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            centerKnobThree.setOnClickListener {
                val knobData = getCenterKnobData(3)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "3rd Knob",
                    branch = knobData.name
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }

            centerKnobFour.setOnClickListener {
                val knobData = getCenterKnobData(4)
                treeOfLifeDialog = TreeofLifeDialogFragment(
                    knobData.data,
                    month = "4th Knob",
                    branch = knobData.name
                )
                treeOfLifeDialog.show(childFragmentManager, "Seasons Dialog")
            }
        }

        observer()
        return binding.root
    }

    private fun observer() {
        binding.loader.root.visible()
        lifecycleScope.launch {
            viewModel.treeOfLifeChannel.collect { events ->
                when (events) {
                    is TreeOfLiveViewModel.TreeOfLiveEvent.Error -> {
                        binding.loader.root.gone()
                        Toast.makeText(requireContext(), events.error, Toast.LENGTH_SHORT).show()
                    }

                    is TreeOfLiveViewModel.TreeOfLiveEvent.Success -> {
                        binding.loader.root.gone()
                        treeOfLifeList.addAll(events.treeData)
                    }

                    is TreeOfLiveViewModel.TreeOfLiveEvent.KnobSuccess -> {
                        binding.loader.root.gone()
                        knobList.addAll(events.knobData)
                    }

                    is TreeOfLiveViewModel.TreeOfLiveEvent.TreeOfLifeInfo -> {
                        binding.loader.root.gone()
                        dialogData = events.data
                    }
                }
            }
        }
    }


    private fun getKnobData(knobNumber: Long): TreeOfLife {
        var treeOfLife = TreeOfLife()
        treeOfLifeList.forEach {
            if (it.month.equals(knobNumber)) {
                treeOfLife = TreeOfLife(it.month, it.season, it.data)
                return@forEach
            }
        }
        return treeOfLife
    }

    private fun getCenterKnobData(knobNumber: Long): Knob {
        var knob = Knob()
        knobList.forEach {
            if (it.number.equals(knobNumber)) {
                knob = Knob(it.number, it.name, it.data)
                return@forEach
            }
        }
        return knob
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val distanceX = e2.x - e1!!.x
            val distanceY = e2.y - e1.y

            if (Math.abs(distanceX) > Math.abs(distanceY) &&
                Math.abs(distanceX) > SWIPE_THRESHOLD &&
                Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
            ) {
                if (distanceX > 0) {
                    // Swipe right
                    switchToWinterTheme()
                } else {
                    // Swipe left
                    switchToSummerTheme()
                }
                return true
            }
            return false
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.drawer).visible()
    }

    private fun switchToMainTheme() {
        binding.apply {
            treeIv.setImageResource(R.drawable.tree_full_visisble)
            winterSeasonIv.setImageResource(R.drawable.summer_season_img)
        }
    }

    private fun switchToSummerTheme() {
        binding.apply {
            treeIv.setImageResource(R.drawable.tree_left_visible)
            winterSeasonIv.setImageResource(R.drawable.summer_season_img)
            goneViews(
                knobSeven,
                knobEight,
                knobNine,
                knobTen,
                knobEleven,
                knobTwelve,
                centerKnobFour,
                centerKnobTwo
            )

            visibleViews(
                knobOne,
                knobTwo,
                knobThree,
                knobFour,
                knobFive,
                knobSix,
                centerKnobOne,
                centerKnobThree
            )
        }
    }

    private fun switchToWinterTheme() {
        binding.apply {
            treeIv.setImageResource(R.drawable.tree_right_visible)
            winterSeasonIv.setImageResource(R.drawable.winter_season_img)

            goneViews(
                knobOne,
                knobTwo,
                knobThree,
                knobFour,
                knobFive,
                knobSix,
                centerKnobOne,
                centerKnobThree
            )
            visibleViews(
                knobSeven,
                knobEight,
                knobNine,
                knobTen,
                knobEleven,
                knobTwelve,
                centerKnobFour,
                centerKnobTwo
            )
        }
    }

    private fun fadeTransition(drawable: Int) {

        binding.treeIv.apply {
            val newImageDrawable = resources.getDrawable(drawable)
            val transitionDrawable = TransitionDrawable(arrayOf(this.drawable, newImageDrawable))
            setImageDrawable(transitionDrawable)
            transitionDrawable.startTransition(300)
        }
    }
}