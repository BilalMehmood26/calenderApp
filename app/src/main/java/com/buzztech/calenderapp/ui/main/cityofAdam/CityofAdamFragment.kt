package com.buzztech.calenderapp.ui.main.cityofAdam

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentCityofAdamBinding
import com.buzztech.calenderapp.model.CityOfAdam
import com.buzztech.calenderapp.model.Knob
import com.buzztech.calenderapp.model.LotEvents
import com.buzztech.calenderapp.model.SwipeHandler
import com.buzztech.calenderapp.model.TreeOfLife
import com.buzztech.calenderapp.ui.component.CityOfAdamDialogFragment
import com.buzztech.calenderapp.ui.component.TreeofLifeDialogFragment
import com.buzztech.calenderapp.utils.gone
import com.buzztech.calenderapp.utils.goneViews
import com.buzztech.calenderapp.utils.postDelayed
import com.buzztech.calenderapp.utils.visible
import com.buzztech.calenderapp.utils.visibleViews
import com.daimajia.androidanimations.library.YoYo

import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CityofAdamFragment : Fragment() {

    companion object {
        fun newInstance() = CityofAdamFragment()
    }

    private var _binding: FragmentCityofAdamBinding? = null
    private val binding get() = _binding!!

    private var initialScaleY: Float = 0f
    private var contentImageHeightConstraint: Int = 0
    private var swipe = SwipeHandler.HOME
    private var swipeCount: Int = 0


    private lateinit var goldPlateDialog: CityOfAdamDialogFragment
    private lateinit var treeOfLifeDialog: TreeofLifeDialogFragment

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var viewModel: CityofAdamViewModel
    private lateinit var gestureDetector: GestureDetector

    private var treeOfLifeList: ArrayList<TreeOfLife> = ArrayList()
    private var knobList: ArrayList<Knob> = ArrayList()
    private var cityOfAdamList: ArrayList<CityOfAdam> = ArrayList()
    private var lotEventsList: ArrayList<LotEvents> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityofAdamBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(CityofAdamViewModel::class.java)

        binding.apply {
            gestureDetector = GestureDetector(requireContext(), GestureListener())
            /*cityOfAdamIv.setOnClickListener {
                goldPlateDialog = CityOfAdamDialogFragment(cityOfAdamList)
                goldPlateDialog.show(childFragmentManager, "Gold Plate")
            }*/
            requireActivity().findViewById<ImageView>(R.id.drawer).visible()

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

            /* goldNotePlate.setOnClickListener {
                 initialScaleY = goldNotePlate.scaleY
                 it.pivotY = 1f
                 val scaleXAnimator = ObjectAnimator.ofFloat(goldNotePlate, View.SCALE_X, 1.0f, 1.0f)
                 val scaleYAnimator = ObjectAnimator.ofFloat(
                     goldNotePlate,
                     View.SCALE_Y,
                     1.0f,
                     contentImageHeightConstraint.toFloat()
                 )

                 scaleYAnimator.duration = 1000
                 scaleYAnimator.addListener(object : Animator.AnimatorListener {
                     override fun onAnimationStart(animation: Animator) {}
                     override fun onAnimationEnd(animation: Animator) {
                         goldNotePlate.scaleY = initialScaleY
                         goldNotePlate.gone()
                     }

                     override fun onAnimationCancel(animation: Animator) {}
                     override fun onAnimationRepeat(animation: Animator) {}
                 })

                 cityOfAdamDialog.gone()
                 val scaleAnimatorSet = AnimatorSet()
                 scaleAnimatorSet.interpolator = AccelerateDecelerateInterpolator()
                 scaleAnimatorSet.playTogether(scaleXAnimator, scaleYAnimator)
                 scaleAnimatorSet.start()
             }*/
        }

        //repeatCall()
        getCurrentUTCTime()
        switchToMain()
        observer()
        //slideUp()
        // slideDown()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observer() {
        lifecycleScope.launch {
            viewModel.cityofAdamEventChannels.collect { event ->
                binding.loader.root.visible()
                when (event) {
                    is CityofAdamViewModel.CityofAdamEvent.Error -> {
                        binding.loader.root.gone()
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }

                    is CityofAdamViewModel.CityofAdamEvent.CityOfAdamList -> {
                        binding.loader.root.gone()
                        cityOfAdamList.addAll(event.cityList)
                    }

                    is CityofAdamViewModel.CityofAdamEvent.LotEventsList -> {
                        binding.loader.root.gone()
                        lotEventsList.addAll(event.lotEventList)
                        getCurrentUTCTime()
                    }

                    is CityofAdamViewModel.CityofAdamEvent.KnobSuccess -> {
                        binding.loader.root.gone()
                        knobList.addAll(event.knobData)
                    }

                    is CityofAdamViewModel.CityofAdamEvent.Success -> {
                        binding.loader.root.gone()
                        treeOfLifeList.addAll(event.treeData)
                    }
                }
            }
        }
    }

    private fun swipeDown() {
        binding.apply {
            initialScaleY = goldNotePlate.scaleY
            goldNotePlate.pivotY = 1f
            val scaleXAnimator = ObjectAnimator.ofFloat(goldNotePlate, View.SCALE_X, 1.0f, 1.0f)
            val scaleYAnimator = ObjectAnimator.ofFloat(
                goldNotePlate,
                View.SCALE_Y,
                1.0f,
                contentImageHeightConstraint.toFloat()
            )

            scaleYAnimator.duration = 1000
            scaleYAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    goldNotePlate.scaleY = initialScaleY
                    goldNotePlate.gone()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })

            cityOfAdamDialog.gone()
            val scaleAnimatorSet = AnimatorSet()
            scaleAnimatorSet.interpolator = AccelerateDecelerateInterpolator()
            scaleAnimatorSet.playTogether(scaleXAnimator, scaleYAnimator)
            scaleAnimatorSet.start()
        }
    }

    private fun slideUp() {
        binding.apply {
            goldNotePlate.setOnClickListener {
                val slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
                goldNotePlate.startAnimation(slideUp)
                goldNotePlate.gone()
                cityOfAdamDialog.gone()
                goldClose.gone()
            }
        }
    }

    private fun slideDown() {
        binding.apply {
            cityOfAdamIv.setOnClickListener {
                val slideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
                goldNotePlate.startAnimation(slideDown)
                goldNotePlate.visible()

                postDelayed(500) {
                    cityOfAdamDialog.visible()
                    /* goldClose.apply {
                         alpha = 0f
                         visible()
                         animate()
                             .alpha(1f)
                             .setDuration(shortAnimationDuration.toLong())
                             .setListener(null)

                     }*/
                }

            }
        }
    }

    private fun repeatCall() {
        countDownTimer = object : CountDownTimer(30000, 10000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {
                repeatCall()
                getCurrentUTCTime()
            }

        }.start()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentUTCTime() {
        val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        binding.timeTv.text = "${dateFormat.format(Date())} COA"


        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val currentTimeUTC = ZonedDateTime.now(ZoneId.of("UTC")).toLocalTime()
        lotEventsList.forEach { events ->
            val startTime = LocalTime.parse(events.startTime, formatter)
            val endTime = LocalTime.parse(events.endTime, formatter)

            if (currentTimeUTC in startTime..endTime) {
                binding.apply {
                    eventNameTv.text = events.name
                    lotTv.text = events.lot
                }
            }
        }

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
                    if (swipe != SwipeHandler.RIGHT) {
                        if (swipe == SwipeHandler.HOME) {
                            Log.d("swipe", "Swipe right IF: ${swipe.name} ${swipe.number}")
                            switchToWinterTheme()
                            swipe = SwipeHandler.RIGHT
                        } else {
                            Log.d("swipe", "Swipe right ELSE: ${swipe.name}   ${swipe.number}")
                            switchToMain()
                            swipe = SwipeHandler.HOME
                        }
                    }
                } else {
                    // Swipe left
                    if (swipe != SwipeHandler.LEFT) {
                        if (swipe == SwipeHandler.HOME) {
                            switchToSummerTheme()
                            swipe = SwipeHandler.LEFT
                        } else {
                            Log.d("swipe", "Swipe right ELSE: ${swipe.name}   ${swipe.number}")
                            switchToMain()
                            swipe = SwipeHandler.HOME
                        }
                    }
                }
                return true
            }
            return false
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
                centerKnobTwo,
                eventNameTv,
                lotTv,
                timeTv,
                goldPlateCityOfAdamGroup
            )
            countDownTimer.cancel()

            visibleViews(
                knobOne,
                knobTwo,
                knobThree,
                knobFour,
                knobFive,
                knobSix,
                centerKnobOne,
                centerKnobThree,
                goldPlateTreeOfLifeGroup,
                winterSeasonIv
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
                centerKnobThree,
                eventNameTv,
                lotTv,
                timeTv,
                goldPlateCityOfAdamGroup
            )
            countDownTimer.cancel()

            visibleViews(
                knobSeven,
                knobEight,
                knobNine,
                knobTen,
                knobEleven,
                knobTwelve,
                centerKnobFour,
                centerKnobTwo,
                goldPlateTreeOfLifeGroup,
                winterSeasonIv
            )
        }
    }

    private fun switchToMain() {

        binding.apply {
           // treeIv.setImageResource(R.drawable.tree_full_visisble)
            fadeTreeTransition(R.drawable.tree_full_visisble)
            goneViews(
                knobSeven,
                knobEight,
                knobNine,
                knobTen,
                knobEleven,
                knobTwelve,
                centerKnobFour,
                centerKnobTwo,
                knobOne,
                knobTwo,
                knobThree,
                knobFour,
                knobFive,
                knobSix,
                centerKnobOne,
                centerKnobThree,
                winterSeasonIv,
                goldPlateTreeOfLifeGroup
            )
            repeatCall()

            visibleViews(
                eventNameTv,
                lotTv,
                timeTv,
                goldPlateCityOfAdamGroup
            )
        }
    }

    private fun fadeTreeTransition(drawable: Int) {

        binding.treeIv.apply {
            val newImageDrawable = resources.getDrawable(drawable)
            val transitionDrawable = TransitionDrawable(arrayOf(this.drawable, newImageDrawable))
            setImageDrawable(transitionDrawable)
            transitionDrawable.startTransition(200)
        }
    }

    private fun fadeTitleTransition(drawable: Int) {

        binding.winterSeasonIv.apply {
            val newImageDrawable = resources.getDrawable(drawable)
            val transitionDrawable = TransitionDrawable(arrayOf(this.drawable, newImageDrawable))
            setImageDrawable(transitionDrawable)
            transitionDrawable.startTransition(1000)
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

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.drawer).visible()
        countDownTimer.start()
    }

    override fun onPause() {
        super.onPause()
        countDownTimer.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        countDownTimer.cancel()
    }
}