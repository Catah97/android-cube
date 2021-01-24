package com.example.cube.`object`

import com.example.cube.`object`.structs.Connection
import com.example.cube.`object`.structs.Point

abstract class Object {

    abstract val points: List<Point>
    abstract val connections: List<Connection>
}