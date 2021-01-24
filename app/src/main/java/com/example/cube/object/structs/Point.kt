package com.example.cube.`object`.structs

import org.apache.commons.math3.linear.MatrixUtils


data class Point(
    val x: Double,
    val y: Double,
    val z: Double
) {

    constructor(x: Int, y: Int, z: Int): this(x.toDouble(), y.toDouble(), z.toDouble())

    fun toVector() = MatrixUtils.createRealVector(
        doubleArrayOf(x, y, z)
    )
}
