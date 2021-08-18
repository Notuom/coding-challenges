package day8

class JumpInstruction(override val value: Int) : Instruction() {
    override val type = "jmp"
    override fun execute(program: Program) {
        program.jump(value)
        executed = true
    }
}
