package com.buzztech.calenderapp.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.text.Layout
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.buzztech.calenderapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun postDelayed(ms: Long, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        action.invoke()
    }, ms)
}


fun View?.gone() {
    this?.let { it.visibility = View.GONE }
}

fun View?.visible() {
    this?.let { it.visibility = View.VISIBLE }
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun goneViews(vararg views: View) {
    views.forEach { it.gone() }
}

fun visibleViews(vararg views: View) {
    views.forEach { it.visible() }
}

fun invisibleViews(vararg views: View) {
    views.forEach { it.inVisible() }
}
fun String.hasSpecialCharacters() = contains("[^\\w]".toRegex())

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

fun ViewGroup.getChildRelativePos(child: View): Rect {
    val rect = Rect()
    child.getDrawingRect(rect)
    offsetDescendantRectToMyCoords(child, rect)
    return rect
}

fun Int.pxToDp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    toFloat(),
    context.resources.displayMetrics
)