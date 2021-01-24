package com.example.cube.view.old.structs

import kotlin.random.Random

class Scene {
    var isTouching: Boolean = false
    var scaleFactor: Float = 1.0F
    var angleX: Double = 0.0
    var angleY: Double = 0.0
    var angleZ: Double = 0.0

    var width: Int = -1
    var height: Int = -1

    fun moveAngle() {
        if (!isTouching) {
            angleX = angleX.moveAngle()
            angleY = angleY.moveAngle()
            angleZ = angleZ.moveAngle()
        }
    }
}

private fun Double.moveAngle(): Double {
    val newAngle = Random.nextDouble(MIN_ANIMATION_MOVE, MAX_ANIMATION_MOVE)
    return moveAngle(newAngle)
}

fun Double.moveAngle(angle: Double): Double {
    val result = this + angle
    return if (result > MAX_ANGLE) {
        0.0
    } else {
        result
    }
}

private const val MAX_ANGLE = Math.PI * 2
private const val MAX_ANIMATION_MOVE = 0.05
private const val MIN_ANIMATION_MOVE = 0.03