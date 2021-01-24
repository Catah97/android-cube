package com.example.cube.view.old.structs

class Scene {
    var isTouching: Boolean = false
    var scaleFactor: Float = 1.0F
    var angleX: Double = 0.0
    var angleY: Double = 0.0
    var angleZ: Double = 0.0

    var width: Int = -1
    var height: Int = -1

    fun move() {
        if (!isTouching) {
            angleX = angleX.move()
            angleY = angleY.move()
            angleZ = angleZ.move()
        }
    }

    private fun Double.move(): Double {
        val result =  this + ANIMATION_MOVE
        return if (result > MAX_ANGLE) {
            0.0
        } else {
            result
        }
    }

    companion object {
        private val MAX_ANGLE = Math.PI * 2
        private val ANIMATION_MOVE = 0.05

    }

}