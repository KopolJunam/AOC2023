import java.io.File

val maxValue = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

fun main() {
    println(
        File("c:/temp/aoc2.txt").inputStream().bufferedReader().lines().
        map { line -> line.split(":", ";", ",") }.
        mapToInt {
            lineTokens ->
            var gameNo = 0
            if(lineTokens.stream().
                peek {
                    token -> gameNo = if(token.startsWith("Game"))
                                        Integer.parseInt(token.split(" ")[1])
                                      else
                                        gameNo
                }.
                filter { !it.startsWith("Game") }.
                noneMatch { val entry = it.trim().split(" ")
                            Integer.parseInt(entry[0]) > maxValue[entry[1]]!! }
            )
                gameNo
            else
                0
        }.
        sum()
    )
}
