package day8

import java.io.File
import java.lang.RuntimeException

fun main() {
    val instructions = mutableListOf<Instruction>()
    File("day8.txt").forEachLine { line ->
        Regex("""^([a-z]{3})\s+([+\-])(\d+)$""").find(line)?.groupValues?.let { (_, instruction, sign, valueString) ->
            val multiplier = if (sign == "+") 1 else -1
            val value = valueString.toInt() * multiplier
            val lineInstruction = when (instruction) {
                "nop" -> NoopInstruction(value)
                "jmp" -> JumpInstruction(value)
                "acc" -> AccumulateInstruction(value)
                else -> throw RuntimeException("Unsupported line found: $line")
            }
            instructions += lineInstruction
        }
    }
    val program = Program(instructions)
    program.execute()

    if (!program.isExecutionFinished) {
        println("${program.isExecutionFinished}: ${program.accumulator}")
    }
}
