package day9

import java.io.File

const val TARGET = 1309761972L

fun main() {
    val list = readFileIntoList()

    var startIndex = 0
    var endIndex = 0

    var found = false
    var sum = 0L

    while (!found) {
        when {
            sum == TARGET -> {
                found = true
            }
            sum > TARGET -> {
                sum -= list[startIndex]
                startIndex++
            }
            else -> {
                sum += list[endIndex]
                endIndex++
            }
        }
    }

    val sublist = list.subList(startIndex, endIndex)
    val weakness = sublist.minOrNull()!! + sublist.maxOrNull()!!

    println()
    println("Weakness: $weakness")
}

fun readFileIntoList(): List<Long> {
    val list = mutableListOf<Long>()
    File("day9.txt").forEachLine { list += it.toLong() }

    return list
}
