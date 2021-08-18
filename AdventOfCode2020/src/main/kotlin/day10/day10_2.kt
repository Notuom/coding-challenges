package day10

import java.io.File

class JoltTree {
    private val joltNodes = mutableMapOf<Int, JoltNode>()

    fun get(rating: Int): JoltNode =
        joltNodes.getOrPut(rating) {
            JoltNode(rating)
        }

    fun arrangements(): Long {
        val root = joltNodes[0]
        if (root != null) {
            return root.arrangements()
        }

        return 0
    }

    override fun toString(): String =
        joltNodes.values.toString()
}

class JoltNode(val rating: Int) {
    var arrangementsMemo: Long? = null
    var children: List<JoltNode> = emptyList()

    fun arrangements(): Long {
        if (arrangementsMemo != null) {
            return arrangementsMemo!!
        }

        arrangementsMemo =
            if (children.isEmpty()) 1
            else children.map(JoltNode::arrangements).sum()

        return arrangementsMemo!!
    }

    override fun toString(): String {
        val ratings = children.map { it.rating }
        return "{ \"rating\": $rating, \"children\": $ratings }"
    }
}

fun main() {
    val adapters = mutableListOf(0)
    File("day10.txt").forEachLine { line ->
        adapters += line.toInt()
    }
    adapters.sort()

    val tree = JoltTree()

    adapters.forEach { joltage ->
        val node = tree.get(joltage)
        node.children = adapters.filter { nextJoltage ->
            nextJoltage - joltage in 1..3
        }.map(tree::get)
    }

    println("bleh")
    println(tree)

    println("------")

    // 988872704 -> TOO LOW
    println(tree.arrangements());
}
