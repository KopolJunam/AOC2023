import java.io.File

fun main() {
    solvePart1()
    solvePart2()
 }

fun solvePart1() {
    println(
        File("c:/temp/aoc2.txt").inputStream().bufferedReader().lines().
        map { line -> line.split(":", ";", ",") }.
        mapToInt {
            lineTokens ->
            val maxValue = mapOf("red" to 12, "green" to 13,"blue" to 14)
            var gameNo = 0
            if(lineTokens.stream().
                peek {
                    token -> gameNo = if(token.startsWith("Game"))
                    Integer.parseInt(token.split(" ")[1])
                else
                    gameNo
                }.
                filter { !it.startsWith("Game") }.
                noneMatch {
                    val entry = it.trim().split(" ")
                    Integer.parseInt(entry[0]) > maxValue[entry[1]]!!
                }
            )
                gameNo
            else
                0
        }.
        sum()
    )
}

fun solvePart2() {
    println(
        File("c:/temp/aoc2.txt").inputStream().bufferedReader().lines().
        map { line -> line.split(":", ";", ",") }.
        mapToInt {
            lineTokens ->
            val maximum = mutableMapOf( "red" to 0, "green" to 0, "blue" to 0)
            lineTokens.stream().
            skip(1).
            forEach {
                val entry = it.trim().split(" ")
                maximum.put(entry[1], Math.max(maximum[entry[1]]!!, Integer.parseInt(entry[0])))
            }
            maximum["red"]!! * maximum["green"]!! * maximum["blue"]!!
        }.
        sum()
    )
}
