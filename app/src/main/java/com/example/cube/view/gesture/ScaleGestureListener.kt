package com.example.cube.view.gesture

import android.view.ScaleGestureDetector
import com.example.cube.view.old.structs.Scene
import kotlin.math.max
import kotlin.math.min

class ScaleGestureListener(
    private val scenePosition: Scene
): ScaleGestureDetector.SimpleOnScaleGestureListener() {


    override fun onScale(detector: ScaleGestureDetector): Boolean {
        val scaleFactor = scenePosition.scaleFactor * detector.scaleFactor
        scenePosition.scaleFactor = max(0.5f, min(scaleFactor, 2.0f))
        return true
    }

}