package com.example.cube.view.old.structs

import com.example.cube.`object`.structs.Connection

data class ProjectedConnection(
    val origin: Connection,
    val p1: ProjectedPoint,
    val p2: ProjectedPoint
)
