package com.example.cube.view.old.structs

import com.example.cube.`object`.structs.Connection
import com.example.cube.`object`.structs.Wall

data class ProjectedWall(
        val origin: Wall,
        val colors: Int,
        val points: List<ProjectedPoint>
)