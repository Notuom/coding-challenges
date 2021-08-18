package day8

class Program(private val instructions: List<Instruction>) {
    var isExecutionFinished = false
    var accumulator: Int = 0
    var instructionIndex: Int = 0

    fun jump(deltaInstructionIndex: Int) {
        val nextIndex = instructionIndex + deltaInstructionIndex
        if (nextIndex >= instructions.size) {
            isExecutionFinished = true
        }

        instructionIndex = nextIndex
    }

    fun execute(): Boolean {
        while (true) {
            val instruction = instructions[instructionIndex]

            if (instruction.executed) {
                return false
            }

//            println("Executing $instructionIndex -> $instruction")
            instruction.execute(this)
            if (isExecutionFinished) {
                return true
            }
        }
    }
}
