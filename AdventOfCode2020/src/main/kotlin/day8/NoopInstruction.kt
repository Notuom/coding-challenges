package day8

class NoopInstruction(override val value: Int) : Instruction() {
    override val type = "nop"
    override fun execute(program: Program) {
        program.jump(1)
        executed = true
    }
}
