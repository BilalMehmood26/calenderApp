package com.buzztech.calenderapp.ui.component.wheelComponent

import android.graphics.Matrix
import android.graphics.Point


internal class Circle() {
    var cx = 0f
        private set
    var cy = 0f
        private set
    var radius = 0f
        private set
    private val matrix: Matrix

    init {
        matrix = Matrix()
    }

    constructor(width: Float, height: Float) : this() {
        cx = width / 2f
        cy = height / 2f
        radius = Math.min(cx, cy)
    }

    fun contains(x: Float, y: Float): Boolean {
        var x = x
        var y = y
        x = cx - x
        y = cy - y
        return x * x + y * y <= radius * radius
    }

    fun rotate(angle: Float, x: Float, y: Float): Point {
        // This is to onRotate about the Rectangles center
        matrix.setRotate(angle, cx, cy)

        // Create new float[] to hold the rotated coordinates
        val pts = FloatArray(2)

        // Initialize the array with our Coordinate
        pts[0] = x
        pts[1] = y

        // Use the Matrix to map the points
        matrix.mapPoints(pts)

        // NOTE: pts will be changed by transform.mapPoints call
        // after the call, pts will hold the new cooridnates

        // Now, create a new Point from our new coordinates
        return Point(pts[0].toInt(), pts[1].toInt())
    }
}