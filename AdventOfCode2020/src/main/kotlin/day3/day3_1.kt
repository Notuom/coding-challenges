import java.io.File

fun main() {
    val TREE = '#'
    val DELTA_X = 3

    var x = 0
    var trees = 0

    File("day3.txt").forEachLine { line ->
        if (line[x] == TREE) {
            trees++
        }

        x = (x + DELTA_X) % line.length
    }

    println(trees)
}
