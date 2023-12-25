package com.example.cube.`object`

import com.example.cube.`object`.structs.Connection
import com.example.cube.`object`.structs.Point
import com.example.cube.`object`.structs.Wall

class CubeOut: Object() {

    override val points: List<Point>
        get() = listOf(
            Point(-1, 5, 1),
            Point(1, 5, 1),
            Point(1, 3, 1),
            Point(-1, 3, 1),
            Point(-1, 5, -1),
            Point(1, 5, -1),
            Point(1, 3, -1),
            Point(-1, 3, -1),
        )

    override val connections: List<Connection>
        get() = listOf(
            Connection(points[0], points[1]),
            Connection(points[1], points[2]),
            Connection(points[2], points[3]),
            Connection(points[3], points[0]),

            Connection(points[4], points[5]),
            Connection(points[5], points[6]),
            Connection(points[6], points[7]),
            Connection(points[7], points[4]),

            Connection(points[0], points[4]),
            Connection(points[1], points[5]),
            Connection(points[2], points[6]),
            Connection(points[3], points[7]),
        )

    override val walls: List<Wall>
        get() = listOf(
                Wall(
                        listOf(
                                points[0], points[1], points[2], points[3]
                        )
                ),
                Wall(
                        listOf(
                                points[1], points[2], points[6], points[5]
                        )
                ),
                Wall(
                        listOf(
                                points[4], points[5], points[6], points[7]
                        )
                ),
                Wall(
                        listOf(
                                points[0], points[3], points[7], points[4]
                        )
                ),
                Wall(
                        listOf(
                                points[0], points[1], points[5], points[4]
                        )
                ),
                Wall(
                        listOf(
                                points[2], points[3], points[7], points[6]
                        )
                )
        )
}