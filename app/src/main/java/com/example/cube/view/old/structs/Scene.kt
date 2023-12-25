package com.example.cube.view.old.structs

import java.io.Serializable
import kotlin.random.Random

class Scene: Serializable {
    var isTouching: Boolean = false
    var scaleFactor: Float = 1.0F
    var transformationX: Float = 0F
    var transformationY: Float = 0F
    var transformationZ: Float = 0F
    var angleX: Double = 0.0
    var angleY: Double = 0.0
    var angleZ: Double = 0.0

    var camera: Camera = Camera(
        -1.0,
        1.0,
        -1.0,
        1.0,
        1.0,
        5.0
    )

    fun moveX(value: Float) {
        transformationX += value
    }

    fun moveY(value: Float) {
        transformationY += value
    }

    fun use(scene: Scene) {
        isTouching = scene.isTouching
        scaleFactor = scene.scaleFactor
        angleX = scene.angleX
        angleY = scene.angleY
        angleZ = scene.angleZ
        camera = scene.camera
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