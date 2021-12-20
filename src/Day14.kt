fun main() {


    fun solveAt(input: List<String>, step: Int): Long {
        var pairFrequencies = input.first()
            .windowed(2)
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
        val rules =
            input.subList(2, input.size).map { it.split(" -> ") }.associateBy({ it.first() }) { it.last().first() }
        repeat(step) {
            pairFrequencies = generate(pairFrequencies, rules)
        }
        val charFrequencies = pairFrequencies
            .flatMap { listOf(it.key.first() to it.value, it.key.last() to it.value) }
            .groupingBy { it.first }
            .fold(0L) { accumulator, pair ->  accumulator + pair.second}
            .toMutableMap()
        charFrequencies[input.first().first()] = charFrequencies[input.first().first()]!! + 1
        charFrequencies[input.first().last()] = charFrequencies[input.first().last()]!! + 1
        return charFrequencies.values.maxOrNull()!! / 2 - charFrequencies.values.minOrNull()!! / 2
    }

    fun part1(input: List<String>): Long {
        return solveAt(input, 10)
    }

    fun part2(input: List<String>): Long {
        return solveAt(input, 40)
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day10_test")
//    check(part1(testInput) == 26397L)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}

fun generate(currentFrequencies: Map<String, Long>, rules: Map<String, Char>): Map<String, Long> {
    val ret = mutableMapOf<String, Long>()
    for (entry in currentFrequencies) {
        val c = rules[entry.key]
        if (c != null) {
            ret[entry.key.first().toString() + c] = (ret[entry.key.first().toString() + c] ?: 0) + entry.value
            ret[c + entry.key.last().toString()] = (ret[c + entry.key.last().toString()] ?: 0) + entry.value
        } else {
            ret[entry.key] = (ret[entry.key] ?: 0) + entry.value
        }
    }
    return ret
}