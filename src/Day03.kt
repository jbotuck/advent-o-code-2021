fun main() {
    fun part1(input: List<String>): Int {
        var mostCommonSequence = ""
        var leastCommonSequence = ""
        for (bitPosition in input.first().indices) {
            mostCommonSequence += (input.getMostCommon(bitPosition))
            leastCommonSequence += (if (mostCommonSequence.last() == '1') '0' else '1')
        }

        return mostCommonSequence.toInt(2) * leastCommonSequence.toInt(2)
    }

    fun part2(input: List<String>): Int {
        var mostCommonSequences = input
        var bitPosition = 0
        while (mostCommonSequences.size > 1) {
            val mostCommonValue = mostCommonSequences.getMostCommon(bitPosition)
            mostCommonSequences = mostCommonSequences.filter { it[bitPosition] == mostCommonValue }
            bitPosition++
        }
        bitPosition = 0
        var leastCommonSequences = input
        while (leastCommonSequences.size > 1) {
            val leastCommonValue = if (leastCommonSequences.getMostCommon(bitPosition) == '0') '1' else '0'
            leastCommonSequences = leastCommonSequences.filter { it[bitPosition] == leastCommonValue }
            bitPosition++
        }


        return mostCommonSequences.first().toInt(2) * leastCommonSequences.first().toInt(2)
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 7)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.getMostCommon(bitPosition: Int): Char {
    var oneCount = 0
    forEach { if (it[bitPosition] == '1') oneCount++ }
    return if (oneCount >= size - oneCount) '1' else '0'
}
