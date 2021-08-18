import java.io.File

private const val FRONT = 'F'
private const val BACK = 'B'

private const val LEFT = 'L'
private const val RIGHT = 'R'

private fun binarySearchIndex(sequence: String, max: Int): Int {
    var range = 0..max

    sequence.forEach { char ->
        val delta = (range.last - range.first + 1) / 2
        when (char) {
            FRONT, LEFT -> {
                range = range.first..(range.last - delta)
            }
            BACK, RIGHT -> {
                range = (range.first + delta)..range.last
            }
        }
    }

    return range.first
}

private fun getRowIndex(row: String): Int =
    binarySearchIndex(row, 127)

private fun getColumnIndex(column: String): Int =
    binarySearchIndex(column, 7)

fun main() {
    val seatIds = mutableListOf<Int>()
    File("day5.txt").forEachLine { line ->
        val row = getRowIndex(line.substring(0, 7))
        val col = getColumnIndex(line.substring(7, 10))
        val seatId = row * 8 + col
        seatIds += seatId
    }
    seatIds.sort()
    var previousSeatId = -1
    seatIds.forEach {
        if (previousSeatId != -1 && it != previousSeatId + 1) {
            println("Your seat is: ${it - 1}")
        }
        previousSeatId = it
    }
}
