package day12

import java.io.File

abstract class ShipInstruction {
    abstract fun execute(ship: Ship)
}

class MoveShipInstruction(private val direction: Direction, private val value: Int) : ShipInstruction() {
    override fun execute(ship: Ship) {
        ship.position += when (direction) {
            Direction.NORTH -> Coordinate(0, -value)
            Direction.SOUTH -> Coordinate(0, value)
            Direction.EAST -> Coordinate(value, 0)
            Direction.WEST -> Coordinate(-value, 0)
        }
    }
}

class TurnShipInstruction(private val rotation: Rotation, private val angle: Int) : ShipInstruction() {
    private val orderedDirections = listOf(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)
    override fun execute(ship: Ship) {
        val rotationUnits = angle / 90
        val multiplier = if (rotation == Rotation.RIGHT) 1 else -1
        val currentDirectionIndex = orderedDirections.indexOf(ship.facing)
        var potentiallyBadIndex = (currentDirectionIndex + (rotationUnits * multiplier))
        while (potentiallyBadIndex < 0) {
            potentiallyBadIndex += 4
        }
        val newDirectionIndex = potentiallyBadIndex % 4
        ship.facing = orderedDirections[newDirectionIndex]
    }
}

class ForwardShipInstruction(private val value: Int) : ShipInstruction() {
    override fun execute(ship: Ship) {
        val moveInstruction = MoveShipInstruction(ship.facing, value)
        moveInstruction.execute(ship)
    }
}

class Ship(programFilename: String) {
    var facing: Direction = Direction.EAST
    var position: Coordinate = Coordinate(0, 0)

    init {
        File(programFilename).forEachLine { line ->
            val code = line[0]
            val value = line.substring(1).toInt()
            val instruction: ShipInstruction = when (code) {
                'N' -> MoveShipInstruction(Direction.NORTH, value)
                'S' -> MoveShipInstruction(Direction.SOUTH, value)
                'E' -> MoveShipInstruction(Direction.EAST, value)
                'W' -> MoveShipInstruction(Direction.WEST, value)
                'L' -> TurnShipInstruction(Rotation.LEFT, value)
                'R' -> TurnShipInstruction(Rotation.RIGHT, value)
                'F' -> ForwardShipInstruction(value)
                else -> throw IllegalArgumentException("Unknown code $code")
            }
            instruction.execute(this)

            println("$line executed: $facing, $position")
        }

        println("${position.distance()}")
    }
}

fun main() {
    Ship("day12.txt")
}
