package com.example.cube.view.old.structs

import java.io.Serializable
import kotlin.random.Random

class Scene: Serializable {
    var isTouching: Boolean = false
    var scaleFactor: Float = 1.0F
    var angleX: Double = 0.0
    var angleY: Double = 0.0
    var angleZ: Double = 0.0

    fun use(scene: Scene) {
        isTouching = scene.isTouching
        scaleFactor = scene.scaleFactor
        angleX = scene.angleX
        angleY = scene.angleY
        angleZ = scene.angleZ
    }
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