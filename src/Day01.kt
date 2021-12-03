fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        var counter = 0
        for (i in 1 until numbers.size) {
            if (numbers[i] > numbers[i - 1]) counter++
        }
        return counter
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        var counter = 0
        for (i in 3 until numbers.size) {
            if (numbers.getTripleSum(i) > numbers.getTripleSum(i - 1)) counter++
        }
        return counter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

private fun List<Int>.getTripleSum(i: Int): Int {
    return get(i) + get(i - 1) + get(i - 2)
}
