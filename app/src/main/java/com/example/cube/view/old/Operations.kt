package com.example.cube.view

import com.example.cube.view.old.structs.Scene
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealMatrix
import kotlin.math.cos
import kotlin.math.sin

fun projection() = MatrixUtils.createRealMatrix(
    arrayOf(
        doubleArrayOf(
            1.0, 0.0, 0.0
        ),
        doubleArrayOf(
            0.0, 1.0, 0.0
        ),
        doubleArrayOf(
            0.0, 0.0, 1.0
        ),
    )
)

fun scale(scale: Double) = MatrixUtils.createRealMatrix(
    arrayOf(
        doubleArrayOf(
            scale, 0.0, 0.0
        ),
        doubleArrayOf(
            0.0, scale, 0.0
        ),
        doubleArrayOf(
            0.0, 0.0, scale
        ),
    )
)

fun rotation(angle: Double): RealMatrix {
    val rotationX = rotationX(angle)
    val rotationY = rotationY(angle)
    val rotationZ = rotationZ(angle)
    val res = rotationX.multiply(rotationY)
    return res.multiply(rotationZ)
}

fun rotationX(angle: Double) = MatrixUtils.createRealMatrix(
    arrayOf(
        doubleArrayOf(
            1.0, 0.0, 0.0,
        ),
        doubleArrayOf(
            0.0, cos(angle), -sin(angle),
        ),
        doubleArrayOf(
            0.0, sin(angle), cos(angle),
        ),
    )
)

fun rotationY(angle: Double) = MatrixUtils.createRealMatrix(
    arrayOf(
        doubleArrayOf(
            cos(angle), 0.0, sin(angle),
        ),
        doubleArrayOf(
            0.0, 1.0, 0.0,
        ),
        doubleArrayOf(
            -sin(angle), 0.0, cos(angle),
        ),
    )
)

fun rotationZ(angle: Double) = MatrixUtils.createRealMatrix(
    arrayOf(
        doubleArrayOf(
            cos(angle), -sin(angle), 0.0,
        ),
        doubleArrayOf(
            sin(angle), cos(angle), 0.0,
        ),
        doubleArrayOf(
            0.0, 0.0, 1.0

        ),
    )
)

fun transformation(scene: Scene) = MatrixUtils.createRealMatrix(
    arrayOf(
        doubleArrayOf(
            1.0, 0.0, scene.transformationX.toDouble(),
        ),
        doubleArrayOf(
            0.0, 1.0, scene.transformationY.toDouble(),
        ),
        doubleArrayOf(
            0.0, 0.0,  1.0,
        )
    )
)