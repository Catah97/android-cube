package com.example.cube.view.old.structs

import kotlin.math.cos
import kotlin.math.sin
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealMatrix

data class Camera (
    var l: Double,
    var r: Double,
    var b: Double,
    var t: Double,
    var n: Double,
    var f: Double,
) {

    fun createMatrix(): RealMatrix = MatrixUtils.createRealMatrix(
        arrayOf(
            doubleArrayOf(
                2.0/(r-l), 0.0, 0.0, -(r+l)/(r- l),
            ),
            doubleArrayOf(
                0.0, 2.0/(t-b), 0.0, -(t+b)/(t- b),
            ),
            doubleArrayOf(
                0.0, 0.0, -2.0/(f-n), -(t+b)/(t- b),
            ),
            doubleArrayOf(
                0.0, 0.0, 0.0, 1.0,
            ),
        )
    )
}
