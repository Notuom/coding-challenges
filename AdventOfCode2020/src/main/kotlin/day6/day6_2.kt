import java.io.File

fun main() {
    var totalQuestionsAnswered = 0
    val groupQuestionLetters = mutableSetOf<Char>()

    File("day6.txt").forEachLine { line ->
        line.ifEmpty {
            totalQuestionsAnswered += groupQuestionLetters.size
            groupQuestionLetters.clear()
            return@forEachLine
        }
        line.forEach(groupQuestionLetters::add)
    }

    println(totalQuestionsAnswered)
}
