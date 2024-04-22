package com.buzztech.calenderapp.ui.component.curveRecycler

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.RecyclerView
import com.buzztech.calenderapp.R
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.floor
import kotlin.math.sin

class ArcLayout(
    resources: Resources,
    private val screenWidth: Int,
    private val viewWidth: Int,
    private val viewHeight: Int,
): RecyclerView.LayoutManager() {

    private var horizontalScrollOffset = viewWidth / 2
    var scrollEnabled = true

    private val recyclerViewHeight =
        (resources.getDimensionPixelSize(R.dimen.recyclerview_height)).toDouble()

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(MATCH_PARENT, MATCH_PARENT)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        // Looping
        val firstVisiblePosition =
            floor(horizontalScrollOffset.toDouble() / viewWidth.toDouble()).toInt()
        val lastVisiblePosition = (horizontalScrollOffset + screenWidth) / viewWidth

        for (index in firstVisiblePosition..lastVisiblePosition) {
            var recyclerIndex = index % itemCount
            if (recyclerIndex < 0) {
                recyclerIndex += itemCount
            }
            val view = recycler.getViewForPosition(recyclerIndex)
            addView(view)

            layoutChildView(index, viewWidth, view)
        }

        // Remove scrap views
        val scrapListCopy = recycler.scrapList.toList()
        scrapListCopy.forEach {
            recycler.recycleView(it.itemView)
        }
    }

    private fun layoutTarget(i: Int, viewWidthWithSpacing: Int, view: View) {
        val targetWidth = viewWidth
        val targetHeight = viewHeight
        val dw = viewWidth - targetWidth

        val viewLeft = i * viewWidthWithSpacing - horizontalScrollOffset
        val left = viewLeft + dw/2
        val right = left + targetWidth

        val top = height - targetHeight
        val bottom = top + targetHeight

        // Measure
        measureChild(view, targetWidth, targetHeight)
        // Layout
        layoutDecorated(view, left, top, right, bottom)
    }

    private fun layoutChildView(i: Int, viewWidthWithSpacing: Int, view: View) {
        val left = i * viewWidthWithSpacing - horizontalScrollOffset
        val right = left + viewWidth

        val viewCentreX = left + viewWidth / 2
        val s: Double = screenWidth.toDouble() / 2
        val h: Double = recyclerViewHeight

        //yt Code
       /* val radius = screenWidth / 2
        val alpha = (viewCentreX / screenWidth) * PI
        val y = radius - (radius * sin(alpha))*/

        val radius: Double = (h * h + s * s) / (h * 5)
        val cosAlpha = (s - viewCentreX) / radius
        val alpha = acos(MathUtils.clamp(cosAlpha, -1.0, 1.0))
        val yComponent = radius - (radius * sin(alpha))

        val top = (h + yComponent).toInt()
        val bottom = top + viewHeight


        // Measure
        measureChild(view, viewWidth, viewHeight + 200)
        // Layout
        layoutDecorated(view, left, top, right, bottom)
    }

    override fun canScrollHorizontally() = scrollEnabled

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        horizontalScrollOffset += dx
        fill(recycler, state)
        return dx
    }
}