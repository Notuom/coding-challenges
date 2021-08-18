package day8

import java.io.File
import java.lang.RuntimeException
import kotlin.reflect.typeOf

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

    var program: Program

    instructions.forEachIndexed { index, instruction ->
        if (instruction is AccumulateInstruction || instruction is NoopInstruction && instruction.value == 0) {
            println("$index: SKIP")
            return@forEachIndexed
        }

        val instructionsCopy = instructions.toMutableList()

        instructionsCopy.forEach {
            it.executed = false
        }

        instructionsCopy[index] = when (instruction) {
            is NoopInstruction -> {
                println("$index: CHANGE ${instruction.value} nop -> jmp")
                JumpInstruction(instruction.value)
            }
            is JumpInstruction -> {
                println("$index: CHANGE ${instruction.value} jmp -> nop")
                NoopInstruction(instruction.value)
            }
            else -> throw RuntimeException("Uh oh: $instruction")
        }

        program = Program(instructionsCopy)
        program.execute()

        if (program.isExecutionFinished) {
            println("DONE!!! ${program.isExecutionFinished}: ${program.accumulator}")
        }
    }
}
