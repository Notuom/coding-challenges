import java.io.File


fun main() {
    var lineCount = 0
    var validCount = 0
    File("day2.txt").forEachLine { line ->
        Regex("""^(\d+)-(\d+)\s+([a-z]):\s+([a-z]+)$""").find(line)?.let { match ->
            lineCount++
            if (match.groupValues.size == 5) {
                val indices = listOf(match.groupValues[1], match.groupValues[2])
                    .map(String::toInt)
                    .map { it - 1 }
                val char = match.groupValues[3][0]
                val password = match.groupValues[4]

                var matchCount = 0
                indices.forEach {
                    if (password[it] == char) {
                        matchCount++
                    }
                }

                if (matchCount == 1) {
                    validCount++
                }
            } else {
                println("Invalid: $line")
            }
        }
    }

    println("Valid: $validCount. Lines: $lineCount")
}
