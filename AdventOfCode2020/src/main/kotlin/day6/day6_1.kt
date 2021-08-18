import java.io.File

fun main() {
    var groupSize = 0
    var totalQuestionsAnswered = 0
    val groupQuestionAnswers = mutableMapOf<Char, Int>()

    File("day6.txt").forEachLine { line ->
        line.ifEmpty {
            groupQuestionAnswers.forEach { entry ->
                if (entry.value == groupSize) {
                    totalQuestionsAnswered++
                }
            }
            groupQuestionAnswers.clear()
            groupSize = 0
            return@forEachLine
        }
        line.forEach { question ->
            groupQuestionAnswers[question] = groupQuestionAnswers.getOrDefault(question, 0) + 1
        }
        groupSize++
    }

    println(totalQuestionsAnswered)
}
