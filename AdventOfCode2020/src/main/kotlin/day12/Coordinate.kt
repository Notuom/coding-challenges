package day12

import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int) {
    fun distance(): Int =
        abs(x) + abs(y)

    operator fun plus(coordinate: Coordinate): Coordinate =
        Coordinate(x + coordinate.x, y + coordinate.y)

    operator fun minus(coordinate: Coordinate): Coordinate =
        Coordinate(x - coordinate.x, y - coordinate.y)

    operator fun times(coordinate: Coordinate): Coordinate =
        Coordinate(x * coordinate.x, y * coordinate.y)

    override fun toString(): String =
        """{"x": $x, "y": $y}"""
}
