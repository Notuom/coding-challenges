import java.io.File


fun main() {
    var lineCount = 0
    var validCount = 0
    File("day2.txt").forEachLine { line ->
        Regex("""^(\d+)-(\d+)\s+([a-z]):\s+([a-z]+)$""").find(line)?.let { match ->
            lineCount++
            if (match.groupValues.size == 5) {
                val min = match.groupValues[1].toInt()
                val max = match.groupValues[2].toInt()
                val char = match.groupValues[3][0]
                val password = match.groupValues[4]

                var letterCount = 0
                password.forEach { letter ->
                    if (letter == char) {
                        letterCount++
                    }
                }

                if (letterCount >= min && letterCount <= max) {
                    validCount++
                } else {
                    println("$line | min: $min; max: $max; letter: $char; password: $password; count: $letterCount INVALID")
                }
            } else {
                println("Invalid: $line")
            }
        }
    }

    println("Valid: $validCount. Lines: $lineCount")
}
