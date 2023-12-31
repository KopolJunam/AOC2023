import java.io.File

val digitsInWords = mapOf(
    "0" to 0,
    "1" to 1,
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9,
    "zero" to 0,
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun main() {
    val sum1 = File("c:/temp/aoc1.txt").inputStream().bufferedReader().lines().map { it.filter { it.isDigit() } }
        .mapToInt { 10 * it.first().digitToInt() + it.last().digitToInt() }.sum()
    val sum21 = File("c:/temp/aoc1.txt").inputStream().bufferedReader().lines().mapToInt { getNumberFor1(it) }.sum()
    val sum22 = File("c:/temp/aoc1.txt").inputStream().bufferedReader().lines().mapToInt { getNumberFor2(it) }.sum()
    println("Resultat = $sum1 / $sum21 / $sum22")
}

fun getNumberFor1(line: String): Int {
    var first: Int? = null
    var last = 0
    var restline = line

    while (!restline.isEmpty()) {
        digitsInWords.keys.stream().filter { restline.startsWith(it) }.findFirst()
            .ifPresent { first = first ?: digitsInWords[it]; last = digitsInWords[it]!! }
        restline = restline.substring(1)
    }
    return 10 * first!! + last
}

fun getNumberFor2(line: String): Int {
    var first: Int? = null
    var last = 0

    (0..line.length - 1).map { line.substring(it) }.forEach { substring ->
        digitsInWords.keys.stream().filter { substring.startsWith(it) }.findFirst()
            .ifPresent { first = first ?: digitsInWords[it]; last = digitsInWords[it]!! }
    }
    return 10 * first!! + last
}