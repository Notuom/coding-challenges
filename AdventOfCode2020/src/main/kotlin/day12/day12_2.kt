package day12

import java.io.File
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

class Board {
    var ship = Coordinate(0, 0)
    var waypoint = Coordinate(10, -1)
}

abstract class BoardInstruction {
    abstract fun execute(board: Board)
}

class MoveWaypointInstruction(private val direction: Direction, private val value: Int) : BoardInstruction() {
    override fun execute(board: Board) {
        board.waypoint += when (direction) {
            Direction.NORTH -> Coordinate(0, -value)
            Direction.SOUTH -> Coordinate(0, value)
            Direction.EAST -> Coordinate(value, 0)
            Direction.WEST -> Coordinate(-value, 0)
        }
    }
}

class RotateWaypointInstruction(private val rotation: Rotation, private val angle: Int) : BoardInstruction() {
    override fun execute(board: Board) {
        val multiplier = if (rotation == Rotation.LEFT) -1 else 1
        val degrees = angle.toDouble() * multiplier
        val angleSin = sin(toRadians(degrees)).toInt()
        val angleCos = cos(toRadians(degrees)).toInt()

        val relativeWaypoint = board.waypoint - board.ship
        val rotatedWaypoint = Coordinate(
            relativeWaypoint.x * angleCos - relativeWaypoint.y * angleSin,
            relativeWaypoint.x * angleSin + relativeWaypoint.y * angleCos
        )

        board.waypoint = rotatedWaypoint + board.ship
    }
}

class ForwardBoardInstruction(private val value: Int) : BoardInstruction() {
    override fun execute(board: Board) {
        val multiplication = Coordinate(value, value)
        val diff = board.waypoint - board.ship
        val move = diff * multiplication

        board.ship += move
        board.waypoint += move
    }
}

fun main() {
    val board = Board()
    File("day12.txt").forEachLine { line ->
        val code = line[0]
        val value = line.substring(1).toInt()
        val instruction: BoardInstruction = when (code) {
            'N' -> MoveWaypointInstruction(Direction.NORTH, value)
            'S' -> MoveWaypointInstruction(Direction.SOUTH, value)
            'E' -> MoveWaypointInstruction(Direction.EAST, value)
            'W' -> MoveWaypointInstruction(Direction.WEST, value)
            'L' -> RotateWaypointInstruction(Rotation.LEFT, value)
            'R' -> RotateWaypointInstruction(Rotation.RIGHT, value)
            'F' -> ForwardBoardInstruction(value)
            else -> throw IllegalArgumentException("Unknown code $code")
        }
        instruction.execute(board)

        println("$line executed. {\"ship\": ${board.ship}, \"waypoint\": ${board.waypoint}}")
    }
    println("${board.ship.distance()}")
}
