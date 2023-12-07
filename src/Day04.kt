import java.io.File

fun main() {
    solveData4_1()
    solveDay4_2()
}
fun solveData4_1() {
    println(
        File("c:/temp/aoc4.txt").inputStream().bufferedReader().lines().
        map { it.replace("  ", " ") }.
        map {
            val tokens = it.split(":", "|")
            tokens[1].trim().split(" ").intersect(tokens[2].trim().split(" "))
        }.
        filter { it.isNotEmpty() }.
        mapToInt { 1 shl (it.size-1) }.
        sum()
    )
}

fun solveDay4_2() {
    val cardMap = mutableMapOf<Int, Int>()

    File("c:/temp/aoc4.txt").inputStream().bufferedReader().lines().
    forEach {
        val tokens = it.split(":", "|")
        val cardNo = Integer.parseInt(tokens[0].split("\\s+".toRegex())[1])
        val noOfWinners =  tokens[1].trim().split("\\s+".toRegex()).intersect(tokens[2].trim().split("\\s+".toRegex())).size
        cardMap.put(cardNo,noOfWinners)
    }
    println(sumUpRange(cardMap, 1..cardMap.size))
}

fun sumUpRange(cardMap : Map<Int, Int>, cardRange: IntRange) : Int {
    return cardRange.map { cardNo ->
        if (cardMap[cardNo] == 0)
            1
        else
            sumUpRange(cardMap, cardNo + 1..cardNo + cardMap[cardNo]!!) + 1
    }.
    sum()
}
