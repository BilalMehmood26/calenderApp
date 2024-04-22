package com.buzztech.calenderapp.ui.main.cycleoftheyear

import android.annotation.SuppressLint
import android.graphics.PointF
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentCycleOfYearBinding
import com.buzztech.calenderapp.ui.component.SimpleTextDialogFragment
import com.buzztech.calenderapp.utils.gone
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.atan2


class CycleOfYearFragment : Fragment(), GestureDetector.OnGestureListener {

    companion object {
        fun newInstance() = CycleOfYearFragment()
    }


    private var dialogData: String? = ""

    private var currentAngle = 0f
    private var startAngle = 0f
    private var lastAngle: Float = 0f

    private var mCurrAngle = 0.0
    private var mPrevAngle = 0.0
    private lateinit var rotateAnimation: RotateAnimation
    private var currentRotation: Float = 0f // Keep track of the current rotation

    private lateinit var animation: FlingAnimation
    private val positiveSpringValueRange = 40.0..340.0
    private val positiveAutumnValueRange = 50.0..140.0

    private val firebaseRef = FirebaseFirestore.getInstance()

    private var _binding: FragmentCycleOfYearBinding? = null
    private val binding get() = _binding!!

    private lateinit var textDialogFragment: SimpleTextDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCycleOfYearBinding.inflate(layoutInflater)

        binding.apply {
            icBack.setOnClickListener {
                findNavController().popBackStack()
            }

            requireActivity().findViewById<ImageView>(R.id.drawer).gone()
        }
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCycleOfYear()
        binding.apply {
            val gestureDetector = GestureDetector(context, this@CycleOfYearFragment)
            rotationImg.setOnTouchListener { v, event ->
               // gestureDetector.onTouchEvent(event)

                //val y: Float = event!!.y
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        /*startAngle = getAngle(event.x, event.y)*/
                        true
                    }

                    MotionEvent.ACTION_MOVE -> {
                      /*  val currentAngle = getAngle(event.x, event.y)
                          val angleDelta = currentAngle - startAngle
                          rotateWheel(angleDelta)
                          startAngle = currentAngle*/
                        val center = PointF(binding.rotationImg.width / 2f, binding.rotationImg.height / 2f)
                        val currentAngle = atan2((event.y - center.y).toDouble(), (event.x - center.x).toDouble()).toFloat()
                        val deltaAngle = currentAngle - lastAngle
                        rotateImage(deltaAngle)
                        lastAngle = currentAngle
                        true
                    }
                    else -> true
                }
            }


            cityOfAdamIv.setOnClickListener {
                textDialogFragment = SimpleTextDialogFragment(data = dialogData!!)
                textDialogFragment.show(childFragmentManager, "Text Dialog")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    /*   private fun rotateWheel(angleDelta: Float = 0f) {
           var newRotation = currentAngle + angleDelta
           newRotation = (newRotation + 360) % 360

           rotateAnimation = RotateAnimation(
               currentAngle,
               newRotation,
               Animation.RELATIVE_TO_SELF,
               0.5f,
               Animation.RELATIVE_TO_SELF,
               0.5f
           )

           Log.d(" rotationAngle", "${round(newRotation)} ")
           rotateAnimation.duration = 0
           rotateAnimation.fillAfter = true
           binding.rotationImg.startAnimation(rotateAnimation)
           currentAngle = newRotation

           when (round(newRotation)) {

               in positiveSpringValueRange -> {
                   binding.bgIv.apply {
                       fadeTransition(R.drawable.bg_spring)
                   }
               }

               in positiveAutumnValueRange -> {
                   binding.bgIv.apply {
                       fadeTransition(R.drawable.bg_autumn)
                   }
               }

               *//*   in posWinterValueRange -> {
                   binding.bgIv.apply {
                       fadeTransition(R.drawable.bg_winter)
                   }
               }

          in posSummerValueRange -> {
                   binding.bgIv.apply {
                       fadeTransition(R.drawable.bg_summer)
                   }
               }
       *//*
        }
    }*/

    private fun getAngle(x: Float, y: Float): Float {
        binding.apply {
            val centerX = rotationImg.width / 2f
            val centerY = rotationImg.height / 2f
            val angle = Math.toDegrees(
                atan2(
                    y.toDouble() - centerY.toDouble(),
                    x.toDouble() - centerX.toDouble()
                )
            )
            return angle.toFloat()
        }
    }


    private fun fadeTransition(drawable: Int) {

        binding.bgIv.apply {
            val newImageDrawable = resources.getDrawable(drawable)
            val transitionDrawable = TransitionDrawable(arrayOf(this.drawable, newImageDrawable))
            setImageDrawable(transitionDrawable)
            transitionDrawable.startTransition(1000)
        }
    }

    private fun getCycleOfYear() {
        firebaseRef.collection("CycleOfYear").document("WxduElZiLbe2PM737Be8").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    dialogData = it.getString("data")
                }
            }.addOnFailureListener { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDown(e: MotionEvent): Boolean {
        startAngle = getAngle(e.x, e.y)
        return true
    }

    override fun onShowPress(e: MotionEvent) {

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        val center = PointF(binding.rotationImg.width / 2f, binding.rotationImg.height / 2f)
        val currentAngle = Math.atan2((e2.y - center.y).toDouble(), (e2.x - center.x).toDouble()).toFloat()
        val deltaAngle = currentAngle - lastAngle
        rotateImage(deltaAngle)
        lastAngle = currentAngle
        return true
    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        e2?.let {
        /*    val currentAngle = getAngle(it.x, it.y)
            val angleDelta = currentAngle - startAngle
            rotateWheel(angleDelta)
            startAngle = currentAngle
*/
           /* val adjustedVelocityX = velocityX * 0.1f
            binding.textView.text = angleDelta.toString()
            animation = FlingAnimation(binding.rotationImg, FlingAnimation.ROTATION)
            animation.setStartVelocity(adjustedVelocityX)
            animation.setFriction(0.5f)
            animation.start()*/
        }
        return true
    }


    private fun rotateImage(deltaAngle: Float) {
        val flingAnimation = FlingAnimation( binding.rotationImg, DynamicAnimation.ROTATION)
        flingAnimation.setStartVelocity(Math.toDegrees(deltaAngle.toDouble()).toFloat() * 60) // Adjust the multiplier to control the rotation speed
        flingAnimation.friction = 0.4f // Adjust the friction for smoother animation
        flingAnimation.start()
    }
    private fun rotateWheel(angleDelta: Float) {
        binding.rotationImg.rotation += angleDelta
    }
}