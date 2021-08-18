package day9

import java.io.File
import kotlin.system.exitProcess

const val LENGTH = 25

fun main() {
    val list = mutableListOf<Long>()
    File("day9.txt").forEachLine { line ->
        val number = line.toLong()

        if (list.size < LENGTH) {
            list += number
            return@forEachLine
        }

        var numberFound = false
        list.forEach { numberA ->
            list.forEach { numberB ->
                if (numberA + numberB == number) {
                    numberFound = true
                }
            }
        }

        if (!numberFound) {
            println("Found number which didn't add up: $number")
            exitProcess(1)
        }

        list += number
        list.removeAt(0)
    }
}
