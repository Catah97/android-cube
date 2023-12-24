package com.example.cube.`object`

import com.example.cube.`object`.structs.Connection
import com.example.cube.`object`.structs.Point
import com.example.cube.`object`.structs.Wall

class Axes: Object() {

    override val points: List<Point>
        get() = listOf(
            Point(-1000, 0, 0),
            Point(1000, 0, 0),
            Point(0,-1000, 0),
            Point(0, 1000, 0),
            Point(0,0,-1000),
            Point(0, 0,1000),
        )
    override val connections: List<Connection>
        get() = listOf(
            Connection(points[0], points[1]),
            Connection(points[2], points[3]),
            Connection(points[4], points[5]),
        )
    override val walls: List<Wall>
        get() = emptyList()
}