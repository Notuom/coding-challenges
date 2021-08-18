package day10

import java.io.File

fun main() {
    val adapters = mutableListOf<Int>()
    File("day10.txt").forEachLine { line ->
        adapters += line.toInt()
    }
    adapters.sort()

    // Phone joltage has a difference of 3
    val differences = mutableMapOf(Pair(3, 1))

    // Wall joltage is 0
    var previousJoltage = 0
    adapters.forEach { joltage ->
        val delta = joltage - previousJoltage

        val currentCount = differences.getOrDefault(delta, 0)
        differences[delta] = currentCount + 1

        previousJoltage = joltage
    }

    val oneByThree = differences[1]!! * differences[3]!!

    println(differences)
    println(oneByThree)
}
