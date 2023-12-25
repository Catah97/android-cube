package com.example.cube.view.old.structs

import com.example.cube.`object`.structs.Point
import org.apache.commons.math3.linear.RealVector

data class ProjectedPoint(
    val x: Float,
    val y: Float,
    val z: Float,
) {
    constructor(vector: RealVector): this(
            vector.getEntry(0).toFloat(),
            vector.getEntry(1).toFloat(),
            vector.getEntry(2).toFloat(),
    )
}
