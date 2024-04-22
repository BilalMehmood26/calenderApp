package com.buzztech.calenderapp.ui.component.wheelComponent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.RectF
import android.os.Build
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.buzztech.calenderapp.R


class SpinningWheelView : View, WheelRotation.RotationListener {
    // endregion
    // region attr
    @ColorInt
    private var wheelStrokeColor = 0
    private var wheelStrokeWidth = 0f
    private var wheelStrokeRadius = 0f
    private var wheelTextColor = 0
    private var wheelTextSize = 0f
    private var wheelArrowColor = 0
    private var wheelArrowWidth = 0f
    private var wheelArrowHeight = 0f
    private var wheelRotation: WheelRotation? = null
    private var circle: Circle? = null
    private var angle = 0f
    private var previousX = 0f
    private var previousY = 0f
    private var items: List<*>? = null
    private var points: Array<Point?>? = null

    @ColorInt
    private lateinit var colors: IntArray
    var onRotationListener: OnRotationListener<String>? = null
    private var onRotationListenerTicket = false
    private var onRotation = false
    private var textPaint: Paint? = null
    private var strokePaint: Paint? = null
    private var trianglePaint: Paint? = null
    private var itemPaint: Paint? = null

    // endregion
    // region constructor
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initAttrs(attrs)
    }

    // endregion
    // region life cycle
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initCircle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (circle == null) {
            initCircle()
        }
        if (hasData() && (points == null || points!!.size != itemSize)) {
            initPoints()
        }
        drawCircle(canvas)
        drawWheel(canvas)
        drawWheelItems(canvas)
        drawTriangle(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (circle == null || !isEnabled || onRotation) {
            return false
        }
        val x = event.x
        val y = event.y
        if (!circle!!.contains(x, y)) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> onRotationListenerTicket = true
            MotionEvent.ACTION_MOVE -> {
                var dx = x - previousX
                var dy = y - previousY

                // reverse direction of rotation above the mid-line
                if (y > circle!!.cy) {
                    dx = dx * -1
                }

                // reverse direction of rotation to left of the mid-line
                if (x < circle!!.cx) {
                    dy = dy * -1
                }
                rotate((dx + dy) * TOUCH_SCALE_FACTOR)
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> onRotationListenerTicket = false
        }
        previousX = x
        previousY = y
        return true
    }

    // endregion
    // region rotation listener
    override fun onRotate(angle: Float) {
        rotate(angle)
    }

    override fun onStop() {
        onRotation = false
        if (onRotationListener != null) {
            onRotationListener!!.onStopRotation(getSelectedItem()!!)
        }
    }

    // endregion
    // region Functionality
    // angle mod 360 prevent to big angle, and overflow float
    // rotate without animation
    fun rotate(angle: Float) {
        this.angle += angle
        this.angle %= ANGLE
        invalidate()
        if (onRotationListenerTicket && angle != 0f && onRotationListener != null) {
            onRotationListener!!.onRotation()
            onRotationListenerTicket = false
        }
    }

    /**
     * Rotate wheel with animation
     *
     * @param maxAngle: Max angle for render rotation
     * @param duration: time in millis wheel for rotation
     * @param interval: time to render rotation
     */
    fun rotate(maxAngle: Float, duration: Long, interval: Long) {
        if (maxAngle == 0f) {
            return
        }
        onRotationListenerTicket = true
        onRotation = true
        wheelRotation?.cancel()
        wheelRotation = WheelRotation
            .init(duration, interval)
            .setMaxAngle(maxAngle)
            .setListener(this)
        wheelRotation!!.start()
    }

    fun getWheelStrokeColor(): Int {
        return wheelStrokeColor
    }

    fun setWheelStrokeColor(wheelStrokeColor: Int) {
        this.wheelStrokeColor = wheelStrokeColor
        invalidate()
    }

    fun getWheelStrokeWidth(): Float {
        return wheelStrokeWidth
    }

    fun setWheelStrokeWidth(wheelStrokeWidth: Float) {
        this.wheelStrokeWidth = wheelStrokeWidth
        initWheelStrokeRadius()
        invalidate()
    }

    fun getWheelTextSize(): Float {
        return wheelTextSize
    }

    fun setWheelTextSize(wheelTextSize: Float) {
        this.wheelTextSize = wheelTextSize
        invalidate()
    }

    fun getWheelTextColor(): Int {
        return wheelTextColor
    }

    fun setWheelTextColor(wheelTextColor: Int) {
        this.wheelTextColor = wheelTextColor
        invalidate()
    }

    fun getWheelArrowColor(): Int {
        return wheelArrowColor
    }

    fun setWheelArrowColor(wheelArrowColor: Int) {
        this.wheelArrowColor = wheelArrowColor
        invalidate()
    }

    fun setWheelArrowWidth(wheelArrowWidth: Float) {
        this.wheelArrowWidth = wheelArrowWidth
        invalidate()
    }

    fun setWheelArrowHeight(wheelArrowHeight: Float) {
        this.wheelArrowHeight = wheelArrowHeight
        invalidate()
    }

    fun getColors(): IntArray {
        return colors
    }

    fun setColors(colors: IntArray) {
        this.colors = colors
        invalidate()
    }

    // Set colors with array res
    // Minimal length 3
    fun setColors(@ArrayRes colorsResId: Int) {
        if (colorsResId == 0) {
            // init default colors
            setColors(COLORS_RES)
            return
        }
        val typedArray: IntArray

        // if in edit mode
        if (isInEditMode) {
            val sTypeArray = resources.getStringArray(colorsResId)
            typedArray = IntArray(sTypeArray.size)
            for (i in sTypeArray.indices) {
                typedArray[i] = Color.parseColor(sTypeArray[i])
            }
        } else {
            typedArray = resources.getIntArray(colorsResId)
        }
        if (typedArray.size < MIN_COLORS) {
            // init default colors
            setColors(COLORS_RES)
            return
        }
        val colors = IntArray(typedArray.size)
        for (i in typedArray.indices) {
            colors[i] = typedArray[i]
        }
        setColors(colors)
    }

    fun getItems(): List<*>? {
        return items
    }

    fun setItems(items: List<*>?) {
        this.items = items
        initPoints()
        invalidate()
    }

    fun setItems(@ArrayRes itemsResId: Int) {
        if (itemsResId == 0) {
            return
        }
        val typedArray = resources.getStringArray(itemsResId)
        val items: MutableList<*> = ArrayList<Any?>()
        for (i in typedArray.indices) {
            items.add(typedArray[i] as Nothing)
        }
        setItems(items)
    }

    fun <T> getSelectedItem(): T? {
        if (circle == null || points == null) {
            return null
        }
        val itemSize = itemSize
        val cx: Float = circle!!.cx
        for (i in points!!.indices) {
            if (points!![i]!!.x <= cx && cx <= points!![(i + 1) % itemSize]!!.x) { // validate point x
                return items!![i] as T
            }
        }
        return null
    }

    // endregion
    // region methods
    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Wheel, 0, 0)
        try {
            // init colors
            val colorsResId = typedArray.getResourceId(R.styleable.Wheel_wheel_colors, 0)
            setColors(colorsResId)
            val wheelStrokeColor = typedArray.getColor(
                R.styleable.Wheel_wheel_stroke_color,
                ContextCompat.getColor(context, R.color.transparent)
            )
            setWheelStrokeColor(wheelStrokeColor)
            val wheelStrokeWidth = typedArray.getDimension(R.styleable.Wheel_wheel_stroke_width, 0f)
            setWheelStrokeWidth(wheelStrokeWidth)
            val itemsResId = typedArray.getResourceId(R.styleable.Wheel_wheel_items, 0)
            setItems(itemsResId)
            val wheelTextSize =
                typedArray.getDimension(R.styleable.Wheel_wheel_text_size, TEXT_SIZE.toFloat())
            setWheelTextSize(wheelTextSize)
            val wheelTextColor = typedArray.getColor(R.styleable.Wheel_wheel_text_color, TEXT_COLOR)
            setWheelTextColor(wheelTextColor)
            val wheelArrowColor =
                typedArray.getColor(R.styleable.Wheel_wheel_arrow_color, ARROW_COLOR)
            setWheelArrowColor(wheelArrowColor)
            val wheelArrowWidth = typedArray.getDimension(
                R.styleable.Wheel_wheel_arrow_width, dpToPx(
                    ARROW_SIZE
                ).toFloat()
            )
            setWheelArrowWidth(wheelArrowWidth)
            val wheelArrowHeight = typedArray.getDimension(
                R.styleable.Wheel_wheel_arrow_height, dpToPx(
                    ARROW_SIZE
                ).toFloat()
            )
            setWheelArrowHeight(wheelArrowHeight)
        } finally {
            typedArray.recycle()
        }
        init()
    }

    private fun init() {
        textPaint = Paint()
        textPaint!!.style = Paint.Style.FILL
        textPaint!!.color = wheelTextColor
        textPaint!!.textSize = wheelTextSize
        strokePaint = Paint()
        strokePaint!!.style = Paint.Style.STROKE
        strokePaint!!.color = wheelStrokeColor
        strokePaint!!.strokeWidth = wheelStrokeWidth
        strokePaint!!.strokeCap = Paint.Cap.ROUND
        trianglePaint = Paint()
        trianglePaint!!.color = wheelArrowColor
        trianglePaint!!.style = Paint.Style.FILL_AND_STROKE
        trianglePaint!!.isAntiAlias = true
        itemPaint = Paint()
        itemPaint!!.style = Paint.Style.FILL
    }

    private fun initWheelStrokeRadius() {
        wheelStrokeRadius = wheelStrokeWidth / 2
        wheelStrokeRadius = if (wheelStrokeRadius == 0f) 1F else wheelStrokeRadius
    }

    private fun initCircle() {
        val width = if (measuredWidth == 0) width else measuredWidth
        val height = if (measuredHeight == 0) height else measuredHeight
        circle = Circle(width.toFloat(), height.toFloat())
    }

    private fun initPoints() {
        if (items != null && !items!!.isEmpty()) {
            points = arrayOfNulls(items!!.size)
        }
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(circle!!.cx, circle!!.cy, circle!!.radius, Paint())
        drawCircleStroke(canvas)
    }

    private fun drawCircleStroke(canvas: Canvas) {
        canvas.drawCircle(
            circle!!.cx, circle!!.cy, circle!!.radius - wheelStrokeRadius,
            strokePaint!!
        )
    }

    private fun drawWheel(canvas: Canvas) {
        if (!hasData()) {
            return
        }

        // Prepare Point
        val cx: Float = circle!!.cx
        val cy: Float = circle!!.cy
        val radius: Float = circle!!.radius
        val endOfRight = cx + radius
        val left = cx - radius + wheelStrokeRadius * 2
        val top = cy - radius + wheelStrokeRadius * 2
        val right = cx + radius - wheelStrokeRadius * 2
        val bottom = cy + radius - wheelStrokeRadius * 2

        // Rotate Wheel
        canvas.rotate(angle, cx, cy)

        // Prepare Pie
        val rectF = RectF(left, top, right, bottom)
        var angle = 0f
        for (i in 0 until itemSize) {
            canvas.save()
            canvas.rotate(angle, cx, cy)
            canvas.drawArc(rectF, 0f, anglePerItem, true, getItemPaint(i)!!)
            canvas.restore()
            points!![i] = circle!!.rotate(angle + this.angle, endOfRight, cy)
            angle += anglePerItem
        }
    }

    private fun drawWheelItems(canvas: Canvas) {
        val cx: Float = circle!!.cx
        val cy: Float = circle!!.cy
        val radius: Float = circle!!.radius
        val x = cx - radius + wheelStrokeRadius * 5
        val textWidth = radius - wheelStrokeRadius * 10
        val textPaint = TextPaint()
        textPaint.set(this.textPaint)
        var angle = anglePerItem / 2
        for (i in 0 until itemSize) {
            val item = TextUtils
                .ellipsize(items!![i].toString(), textPaint, textWidth, TextUtils.TruncateAt.END)
            canvas.save()
            canvas.rotate(angle + 180, cx, cy) // +180 for start from right
            canvas.drawText(item.toString(), x, cy, this.textPaint!!)
            canvas.restore()
            angle += anglePerItem
        }
    }

    private fun drawTriangle(canvas: Canvas) {
        // Prepare Point
        val cx: Float = circle!!.cx
        val cy: Float = circle!!.cy
        val radius: Float =circle!!.radius

        // Handle triangle not following the rotation
        canvas.rotate(-angle, cx, cy)
        drawTriangle(canvas, trianglePaint, cx, cy - radius, wheelArrowWidth, wheelArrowHeight)
    }

    private fun drawTriangle(
        canvas: Canvas,
        paint: Paint?,
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        val halfWidth = width / 2
        val halfHeight = height / 2
        val path = Path()
        path.moveTo(x - halfWidth, y - halfHeight) // Top left
        path.lineTo(x + halfWidth, y - halfHeight) // Top right
        path.lineTo(x, y + halfHeight) // Bottom Center
        path.lineTo(x - halfWidth, y - halfHeight) // Back to top left
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun getItemPaint(position: Int): Paint? {
        var i = position % colors.size

        // if start color == end color, get middle color
        if (itemSize - 1 == position && position % colors.size == 0) {
            i = colors.size / 2
        }
        itemPaint!!.color = colors[i]
        return itemPaint
    }

    private val itemSize: Int
        private get() = if (items == null) 0 else items!!.size
    private val anglePerItem: Float
        private get() = ANGLE / itemSize.toFloat()

    private fun hasData(): Boolean {
        return items != null && !items!!.isEmpty()
    }

    private fun dpToPx(dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    // endregion
    // region Listener
    interface OnRotationListener<T> {
        fun onRotation()
        fun onStopRotation(item: T)
    } // endregion

    companion object {
        // region static attr
        private const val MIN_COLORS = 3
        private const val ANGLE = 360f
        private val COLORS_RES: Int = R.array.rainbow_dash
        private const val TOUCH_SCALE_FACTOR = 180.0f / 320 / 2
        private const val TEXT_SIZE = 25
        private const val TEXT_COLOR = Color.BLACK
        private const val ARROW_COLOR = Color.BLACK
        private const val ARROW_SIZE = 50
    }
}