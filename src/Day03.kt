fun main() {
    fun flip(c: Char) = if (c == '1') '0' else '1'

    fun part1(input: List<String>) = input.first().indices
        .map { input.getMostCommon(it) }
        .fold("" to "") { p, c ->
            p.first + c to p.second + flip(c)
        }.toList()
        .map { it.toInt(2) }
        .reduce(Int::times)


    fun part2(input: List<String>) = sequenceOf(
        input.query { getMostCommon(it) },
        input.query { flip(getMostCommon(it)) }
    ).map { it.toInt(2) }
        .reduce(Int::times)
// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun Collection<String>.getMostCommon(i: Int) =
    groupingBy { it[i] }
        .eachCount()
        .entries.sortedByDescending {it.key}
        .maxByOrNull { it.value }!!
        .key

private fun Collection<String>.query(bitCriteria: Collection<String>.(Int) -> Char): String {
    var results = this
    for (i in first().indices) {
        val criteriaValue = results.bitCriteria(i)
        results = results.filter { it[i] == criteriaValue }
        if (results.size <= 1) break
    }
    return results.first()
}

