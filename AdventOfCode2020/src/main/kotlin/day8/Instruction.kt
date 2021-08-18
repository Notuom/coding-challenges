package day8

abstract class Instruction {
    var executed: Boolean = false
    abstract val type: String
    abstract val value: Int

    abstract fun execute(program: Program)
    override fun toString() = "Instruction { type: $type, value: $value }"
}
