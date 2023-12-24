package com.example.cube.`object`

import com.example.cube.`object`.structs.Connection
import com.example.cube.`object`.structs.Point
import com.example.cube.`object`.structs.Wall

abstract class Object {

    abstract val points: List<Point>
    abstract val connections: List<Connection>
    abstract val walls: List<Wall>
}