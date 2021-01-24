package com.example.cube.matrix

/**
 * Source: https://github.com/yinpeng/kotlin-matrix/blob/master/src/main/kotlin/com/ichipsea/kotlin/matrix/NumberMatrix.kt
 */

operator fun <M : Number, N : Number> Matrix<M>.plus(other: Matrix<N>): Matrix<Double> {
    if (rows !== other.rows || cols !== other.cols)
        throw IllegalArgumentException("Matrices not match")

    return mapIndexed { x, y, value -> value.toDouble() + other[x, y].toDouble() }
}

operator fun <N : Number> Matrix<N>.unaryMinus(): Matrix<Double> {
    return map { -it.toDouble() }
}

operator fun <M : Number, N : Number> Matrix<M>.minus(other: Matrix<N>): Matrix<Double> {
    return this + (-other)
}

operator fun <M : Number, N : Number> Matrix<M>.times(other: Matrix<N>): Matrix<Double> {
    if (cols != other.rows)
        throw IllegalArgumentException("Matrices not match")

    return createZeroMatrix(other.cols, rows).mapIndexed { x, y, _ -> multiplyMatricesCell(this, other, x, y) }
}

fun <M : Number, N : Number> multiplyMatricesCell(
    firstMatrix: Matrix<M>,
    secondMatrix: Matrix<N>,
    x: Int,
    y: Int
): Double {
    var cell = 0.0
    for (i in 0 until secondMatrix.rows) {
        cell += firstMatrix[i, y].toDouble() * secondMatrix[x, i].toDouble()
    }
    return cell
}

operator fun <M : Number> Matrix<M>.times(other: Number): Matrix<Double> {
    return map { it.toDouble() * other.toDouble() }
}

operator fun <M : Number> Number.times(other: Matrix<M>): Matrix<Double> {
    return other * this
}

infix fun <M : Number, N : Number> Matrix<M>.x(other: Matrix<N>): Matrix<Double> {
    if (rows !== other.cols)
        throw IllegalArgumentException("Matrices not match")

    return createMatrix(cols, other.rows) { x, y ->
        var value = .0
        for (i in 0..rows-1)
            value += this[x, i].toDouble() * other[i, y].toDouble()
        value
    }
}