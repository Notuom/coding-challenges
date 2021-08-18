import java.io.File

fun main() {
    val validAttributes = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")
    val passports = mutableListOf<Int>()
    var passportAttributes = 0
    var i = 0
    File("day4.txt").forEachLine { line ->
        i++
        if (line.isEmpty()) {
            if (passportAttributes != 7) {
                println("$passportAttributes; $i")
            }
            passports += passportAttributes
            passportAttributes = 0
            return@forEachLine
        }

        val lineAttributes = line.split(Regex("""\s+"""))
        lineAttributes.forEach { keyValue ->
            val split = keyValue.split(":")

            if (split.size == 2 && split[0] != "cid") {
                passportAttributes++
            }
        }
    }

    val validPassports = passports.filter { it == 7 }
    println(validPassports.size.toString())
}
