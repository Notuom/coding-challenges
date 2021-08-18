package day11

import java.lang.IllegalArgumentException

enum class Seating(private val code: Char) {
    FLOOR('.'),
    EMPTY('L'),
    OCCUPIED('#');

    override fun toString(): String =
        code.toString()

    companion object {
        fun fromCode(code: Char): Seating =
            when (code) {
                '.' -> FLOOR
                'L' -> EMPTY
                '#' -> OCCUPIED
                else -> throw IllegalArgumentException()
            }
    }
}
