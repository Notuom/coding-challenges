package day8

class AccumulateInstruction(override val value: Int) : Instruction() {
    override val type = "acc"
    override fun execute(program: Program) {
        program.accumulator += value
        program.jump(1)
        executed = true
    }
}
