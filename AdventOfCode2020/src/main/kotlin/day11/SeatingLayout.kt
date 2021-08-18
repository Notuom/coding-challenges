package day11

import java.io.File

class SeatingLayout(filename: String) {
    private var lastChangedRows = 0
    private var layout = mutableListOf<MutableList<Seating>>()

    private val width: Int
    private val height: Int

    init {
        File(filename).forEachLine { line ->
            layout.add(line.map { code -> Seating.fromCode(code) }.toMutableList())
        }
        width = layout[0].size
        height = layout.size
    }

    private fun getSeatAt(position: Pair<Int, Int>): Seating? {
        if (position.first in 0 until height && position.second in 0 until width) {
            return layout[position.first][position.second]
        }

        return null
    }

    // Challenge 1
    private fun getAdjacentOccupiedCount(y: Int, x: Int): Int {
        val positions = listOf(
            Pair(y - 1, x - 1), Pair(y - 1, x), Pair(y - 1, x + 1),
            Pair(y, x - 1), Pair(y, x + 1),
            Pair(y + 1, x - 1), Pair(y + 1, x), Pair(y + 1, x + 1)
        )

        return positions.filter { getSeatAt(it) == Seating.OCCUPIED }.size
    }

    // Challenge 2
    private fun getDirectionalOccupiedCount(y: Int, x: Int): Int {
        val directions = listOf(
            Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
            Pair(0, -1), Pair(0, 1),
            Pair(1, -1), Pair(1, 0), Pair(1, 1)
        )
        var occupiedCount = 0

        directions.forEach { direction ->
            var position = Pair(y, x)
            var found = false
            while (!found) {
                position = Pair(
                    position.first + direction.first,
                    position.second + direction.second
                )
                val seat = getSeatAt(position)

                if (seat != Seating.FLOOR) {
                    found = true

                    if (seat == Seating.OCCUPIED) {
                        occupiedCount++
                    }
                }
            }
        }

        return occupiedCount
    }

    private fun copyLayout(): MutableList<MutableList<Seating>> =
        ArrayList(layout).map { ArrayList(it).toMutableList() }.toMutableList()

    private fun getOccupiedCount(): Int =
        layout.flatten().filter { it == Seating.OCCUPIED }.count()

    fun solve(findFirstSeat: Boolean) {
        do {
            lastChangedRows = 0
            val copy = copyLayout()
            layout.forEachIndexed { y, yList ->
                yList.forEachIndexed { x, seating ->
                    if (seating == Seating.EMPTY || seating == Seating.OCCUPIED) {
                        if (!findFirstSeat) {
                            // Challenge 1
                            val occupied = getAdjacentOccupiedCount(y, x)

                            if (seating == Seating.EMPTY && occupied == 0) {
                                copy[y][x] = Seating.OCCUPIED
                                lastChangedRows++
                            } else if (seating == Seating.OCCUPIED && occupied >= 4) {
                                copy[y][x] = Seating.EMPTY
                                lastChangedRows++
                            }
                        } else {
                            // Challenge 2
                            val occupied = getDirectionalOccupiedCount(y, x)

                            if (seating == Seating.EMPTY && occupied == 0) {
                                copy[y][x] = Seating.OCCUPIED
                                lastChangedRows++
                            } else if (seating == Seating.OCCUPIED && occupied >= 5) {
                                copy[y][x] = Seating.EMPTY
                                lastChangedRows++
                            }
                        }
                    }
                }
            }
            layout = copy
        } while (lastChangedRows > 0)
        println("${getOccupiedCount()}")
    }
}
