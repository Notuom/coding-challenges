import java.io.File

const val TREE = '#'

val scenarios = listOf(
    Pair(1, 1),
    Pair(3, 1),
    Pair(5, 1),
    Pair(7, 1),
    Pair(1, 2)
)

fun main() {
    var total = 1L
    val totals = mutableListOf<Long>()
    scenarios.forEach { scenario ->
        var trees = 0L
        val deltaX = scenario.first
        val deltaY = scenario.second

        var x = 0
        var skipped = 0
        var yToSkip = 0

        File("day3.txt").forEachLine { line ->
            if (deltaY > 1) {
                if (yToSkip == 0) {
                    yToSkip = deltaY - 1
                } else if (yToSkip > 0) {
                    yToSkip--
                    skipped++
                    return@forEachLine
                }
            }

            if (line[x] == TREE) {
                trees++
            }

            x = (x + deltaX) % line.length
        }

        totals += trees
        total *= trees
        println("$trees, $total, $skipped")
    }

    println(totals)
}
