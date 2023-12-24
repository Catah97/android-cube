package com.example.cube.view.gesture

import android.view.GestureDetector
import android.view.MotionEvent
import com.example.cube.view.old.structs.Scene
import com.example.cube.view.old.structs.moveAngle
import kotlin.math.atan

class SimpleGestureListener(
    val scene: Scene
): GestureDetector.OnGestureListener {

    var width: Int? = null
    var height: Int? = null

    override fun onDown(e: MotionEvent): Boolean {
        return false
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
        val minimum = minOf(width ?: 0, height ?: 0)
        calculateAngle(minimum, distanceX) {
            scene.angleY = scene.angleY.moveAngle(it)
        }
        calculateAngle(minimum, -distanceY) {
            scene.angleX = scene.angleX.moveAngle(it)
        }
        return false
    }

    override fun onLongPress(e: MotionEvent) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    private fun calculateAngle(size: Int, distance: Float, action: (Double) -> Unit) {
        val angle = calculateAngle(size / 3, distance)
        action(angle)
    }

    private fun calculateAngle(size: Int, distance: Float): Double {
        val value = distance.toDouble() / size
        return atan(value)
    }

    companion object {
        private const val TAG = "SimpleGestureListener"
    }
}