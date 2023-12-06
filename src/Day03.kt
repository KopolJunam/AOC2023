import java.io.File
import java.util.stream.Collectors

const val LINE_LENGTH = 140

data class AsteriscReturn(val pos : Int, val number: Int, val asteriscPosSet: Set<Int>)

fun main() {
    solveDay3_1()
    solveDay3_2()
}
fun solveDay3_1() {
    val source = ".".repeat(LINE_LENGTH) +
                 File("c:/temp/aoc3.txt").inputStream().bufferedReader().lines().collect(Collectors.joining()) +
                 ".".repeat(LINE_LENGTH)
    var curPos = LINE_LENGTH
    val endPos = source.length - LINE_LENGTH - 1
    var sum = 0

    while (curPos < endPos) {
        curPos = skipNonDigits(source, curPos, endPos)
        val result = getNumberAndPos(source, curPos, endPos)
        curPos = result["pos"]!!
        sum += result["number"]!!
    }

    println(sum)
}

fun solveDay3_2() {
    val source = ".".repeat(LINE_LENGTH) +
            File("c:/temp/aoc3.txt").inputStream().bufferedReader().lines().collect(Collectors.joining()) +
            ".".repeat(LINE_LENGTH)
    var curPos = LINE_LENGTH
    val endPos = source.length - LINE_LENGTH - 1
    val asteriscMap = mutableMapOf<Int, List<Int>>()

    while (curPos < endPos) {
        curPos = skipNonDigits(source, curPos, endPos)
        val result = getNumberAndAsteriscList(source, curPos, endPos)
        curPos = result.pos
        if (result.asteriscPosSet.isNotEmpty()) {
            val asteriscPos = result.asteriscPosSet.first() // we know that for this input there is only one asterisc
            if (asteriscMap.containsKey(asteriscPos)) {
                asteriscMap.put(asteriscPos, listOf(asteriscMap[asteriscPos]!!.first(), result.number))
            } else {
                asteriscMap.put(asteriscPos, listOf(result.number))
            }
        }
    }
    println(asteriscMap.values.stream().filter { it.size == 2 }.mapToInt { it[0]*it[1] }.sum())
}

fun skipNonDigits(source: String, startPos : Int, maxEndPos: Int) :Int {
    var curPos = startPos
    while (curPos <= maxEndPos && !source[curPos].isDigit()) {
        curPos++
    }
    return curPos
}

fun getNumberAndPos(source: String, startPos : Int, maxEndPos: Int) : Map<String, Int> {
    var curPos = startPos
    var digits = ""
    var symbolFound = false
    var numResult = 0

    while (curPos <= maxEndPos && source[curPos].isDigit()) {
        digits += source[curPos]
        symbolFound = symbolFound || hasNeighboringSymbol(source, curPos)
        curPos++
    }

    if (symbolFound) {
        numResult = Integer.parseInt(digits)
    }
    return mapOf("number" to numResult, "pos" to curPos)
}

fun getNumberAndAsteriscList(source: String, startPos : Int, maxEndPos: Int) : AsteriscReturn {
    var curPos = startPos
    var digits = ""
    val asteriscPosSet = mutableSetOf<Int>()

    while (curPos <= maxEndPos && source[curPos].isDigit()) {
        digits += source[curPos]
        asteriscPosSet.addAll(hasNeighboringAsteriscs(source,curPos))
        curPos++
    }
    return AsteriscReturn(curPos, Integer.parseInt(digits), asteriscPosSet)
}

fun hasNeighboringSymbol(source: String, pos : Int) : Boolean {
    val onLeftBorder = (pos % LINE_LENGTH) == 0
    val onRightBorder = ((pos+1) % LINE_LENGTH) == 0
    var surroundingSymbols = ""
    if (!onLeftBorder) {
        surroundingSymbols += String(charArrayOf(source[pos-LINE_LENGTH-1], source[pos-1], source[pos-LINE_LENGTH+1]))
    }
    surroundingSymbols += String(charArrayOf(source[pos-LINE_LENGTH], source[pos+LINE_LENGTH]))
    if (!onRightBorder) {
        surroundingSymbols += String(charArrayOf(source[pos+LINE_LENGTH-1], source[pos+1], source[pos+LINE_LENGTH+1]))
    }
    val s = surroundingSymbols.filter { !it.isDigit() && it != '.' }
    return s.isNotEmpty()
}

fun hasNeighboringAsteriscs(source: String, pos : Int) : Set<Int> {
    val onLeftBorder = (pos % LINE_LENGTH) == 0
    val onRightBorder = ((pos+1) % LINE_LENGTH) == 0
    val asteriscPosSet = mutableSetOf<Int>()

    if (!onLeftBorder) {
        asteriscPosSet.addAll(getAsteriscList(source,pos-LINE_LENGTH-1, pos-1, pos-LINE_LENGTH+1))
    }
    asteriscPosSet.addAll(getAsteriscList(source, pos-LINE_LENGTH, pos+LINE_LENGTH))
    if (!onRightBorder) {
        asteriscPosSet.addAll(getAsteriscList(source,pos+LINE_LENGTH-1, pos+1, pos+LINE_LENGTH+1))
    }
    return asteriscPosSet
}

fun getAsteriscList(source : String, vararg positions: Int) : Set<Int> {
    val asteriscPosSet = mutableSetOf<Int>()

    for (pos in positions) {
        if (source[pos] == '*') {
            asteriscPosSet.add(pos)
        }
    }
    return asteriscPosSet
}