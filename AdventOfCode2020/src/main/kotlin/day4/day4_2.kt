import java.io.File

private fun isValidBirthYear(byr: String): Boolean =
    byr.toInt() in 1920..2002

private fun isValidIssueYear(iyr: String): Boolean =
    iyr.toInt() in 2010..2020

private fun isValidExpirationYear(eyr: String): Boolean =
    eyr.toInt() in 2020..2030

private fun isValidHeight(hgt: String): Boolean {
    var valid = false
    Regex("""(\d+)(cm|in)""").find(hgt)?.groupValues?.let { groups ->
        val value = groups[1].toInt()
        val unit = groups[2]

        valid = when (unit) {
            "cm" -> value in 150..193
            "in" -> value in 59..76
            else -> false
        }
    }

    return valid
}

private fun isValidHairColor(hcl: String): Boolean =
    hcl.matches(Regex("""^#[a-f0-9]{6}$"""))

private fun isValidEyeColor(ecl: String): Boolean =
    listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(ecl)

private fun isValidPassportId(pid: String): Boolean =
    pid.matches(Regex("""^[0-9]{9}$"""))

fun main() {
    val validAttributes = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")
    val passports = mutableListOf<Int>()
    var passportAttributes = 0
    var i = 0
    File("day4.txt").forEachLine { line ->
        i++
        if (line.isEmpty()) {
            passports += passportAttributes
            passportAttributes = 0
            return@forEachLine
        }

        val lineAttributes = line.split(Regex("""\s+"""))
        lineAttributes.forEach { keyValue ->
            val split = keyValue.split(":")

            if (split.size == 2 && split[0] != "cid") {
                val valid: Boolean = when (split[0]) {
                    "byr" -> isValidBirthYear(split[1])
                    "iyr" -> isValidIssueYear(split[1])
                    "eyr" -> isValidExpirationYear(split[1])
                    "hgt" -> isValidHeight(split[1])
                    "hcl" -> isValidHairColor(split[1])
                    "ecl" -> isValidEyeColor(split[1])
                    "pid" -> isValidPassportId(split[1])
                    else -> false
                }

                if (valid) {
                    passportAttributes++
                }
            }
        }
    }

    val validPassports = passports.filter { it == 7 }
    println(validPassports.size.toString())
}
